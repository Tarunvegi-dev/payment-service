package com.payment.payment_service.service.impl;

import org.slf4j.MDC;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.payment.payment_service.config.RabbitConfig;
import com.payment.payment_service.dto.CompensatePaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentResponse;
import com.payment.payment_service.enums.PaymentStatus;
import com.payment.payment_service.exception.PaymentNotFoundException;
import com.payment.payment_service.model.Payment;
import com.payment.payment_service.repository.PaymentRepository;
import com.payment.payment_service.service.PaymentService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService{
    
    private final PaymentRepository paymentRepository;
    private final PaymentPersistentServiceImpl paymentPersistentServiceImpl;
    private final RabbitTemplate rabbitTemplate;


    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentPersistentServiceImpl paymentPersistentServiceImpl, RabbitTemplate rabbitTemplate){
        this.paymentRepository = paymentRepository;
        this.paymentPersistentServiceImpl = paymentPersistentServiceImpl;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Transactional
    @RabbitListener(queues = "compensation-requested")
    public void Compensate(CompensatePaymentRequest compensatePaymentRequest){
        Payment payment = paymentRepository.findById(compensatePaymentRequest.getTransactionId())
        .orElseThrow(() -> new PaymentNotFoundException("Payment not found with this transaction id"));

        if(payment.getPaymentStatus() == PaymentStatus.REVERSED) return;

        payment.setPaymentStatus(PaymentStatus.REVERSED);
        paymentRepository.save(payment);

        rabbitTemplate.convertAndSend(RabbitConfig.COMPENSATION_QUEUE, compensatePaymentRequest);
        log.info("Payment compensation completed :{}", compensatePaymentRequest.getTransactionId());
    }


    @Override
    public ProcessPaymentResponse processPayment(ProcessPaymentRequest processPaymentRequest) throws Exception{
        try {
            System.out.println("correlationId" + MDC.get("correlationId"));
            return paymentPersistentServiceImpl.createPayment(processPaymentRequest);
        }catch(DataIntegrityViolationException ex){
            log.warn("Payment already completed ignoring duplicate :{}", processPaymentRequest.getTransactionId());
            Payment payment = paymentRepository.findById(processPaymentRequest.getTransactionId()).get();
            ProcessPaymentResponse processPaymentResponse = new ProcessPaymentResponse();
            processPaymentResponse.setTransactionId(processPaymentRequest.getTransactionId());
            processPaymentResponse.setPaymentStatus(payment.getPaymentStatus());
            return processPaymentResponse;
        }
    }
    
    
}

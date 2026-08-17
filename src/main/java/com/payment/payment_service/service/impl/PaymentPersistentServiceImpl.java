package com.payment.payment_service.service.impl;

import org.springframework.stereotype.Service;

import com.payment.payment_service.dto.ProcessPaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentResponse;
import com.payment.payment_service.enums.PaymentStatus;
import com.payment.payment_service.model.Payment;
import com.payment.payment_service.repository.PaymentRepository;
import com.payment.payment_service.service.PaymentPersistentService;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PaymentPersistentServiceImpl implements PaymentPersistentService{

    private final PaymentRepository paymentRepository;

    public PaymentPersistentServiceImpl(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }


    @Transactional
    public ProcessPaymentResponse createPayment(ProcessPaymentRequest processPaymentRequest){
        ProcessPaymentResponse processPaymentResponse = new ProcessPaymentResponse();
        processPaymentResponse.setTransactionId(processPaymentRequest.getTransactionId());
        Payment payment = new Payment();
        payment.setTransactionId(processPaymentRequest.getTransactionId());
        payment.setAmount(processPaymentRequest.getAmount());
        payment.setPaymentMode(processPaymentRequest.getPaymentMode());
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setTransactionType(processPaymentRequest.getTransactionType());
        paymentRepository.saveAndFlush(payment);
        processPaymentResponse.setPaymentStatus(payment.getPaymentStatus());
        log.info("Payment completed :{}", processPaymentRequest.getTransactionId());
        return processPaymentResponse;
    }
}

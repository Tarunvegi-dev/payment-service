package com.payment.payment_service.service.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.payment.payment_service.dto.ProcessPaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentResponse;
import com.payment.payment_service.model.Payment;
import com.payment.payment_service.repository.PaymentRepository;
import com.payment.payment_service.service.PaymentService;

@Service
public class PaymentServiceImpl implements PaymentService{
    
    private final PaymentRepository paymentRepository;
    private final PaymentPersistentServiceImpl paymentPersistentServiceImpl;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentPersistentServiceImpl paymentPersistentServiceImpl){
        this.paymentRepository = paymentRepository;
        this.paymentPersistentServiceImpl = paymentPersistentServiceImpl;
    }


    @Override
    public ProcessPaymentResponse processPayment(ProcessPaymentRequest processPaymentRequest) throws Exception{
        try {
            return paymentPersistentServiceImpl.createPayment(processPaymentRequest);
        }catch(DataIntegrityViolationException ex){
            Payment payment = paymentRepository.findById(processPaymentRequest.getTransactionId()).get();
            ProcessPaymentResponse processPaymentResponse = new ProcessPaymentResponse();
            processPaymentResponse.setTransactionId(processPaymentRequest.getTransactionId());
            processPaymentResponse.setPaymentStatus(payment.getPaymentStatus());
            return processPaymentResponse;
        }
    }
    
    
}

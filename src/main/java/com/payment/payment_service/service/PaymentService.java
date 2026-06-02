package com.payment.payment_service.service;

import com.payment.payment_service.dto.ProcessPaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentResponse;

public interface PaymentService {
    public ProcessPaymentResponse processPayment(ProcessPaymentRequest processPaymentRequest) throws Exception;
}

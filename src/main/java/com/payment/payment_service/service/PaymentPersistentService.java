package com.payment.payment_service.service;

import com.payment.payment_service.dto.ProcessPaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentResponse;

public interface PaymentPersistentService {
    public ProcessPaymentResponse createPayment(ProcessPaymentRequest processPaymentRequest);
}

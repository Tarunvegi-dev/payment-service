package com.payment.payment_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.payment.payment_service.dto.ProcessPaymentRequest;
import com.payment.payment_service.dto.ProcessPaymentResponse;
import com.payment.payment_service.service.PaymentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }
    
    @PostMapping("/process")
    public ResponseEntity<ProcessPaymentResponse> processPayment(@RequestBody @Valid ProcessPaymentRequest processPaymentRequest) throws Exception{
        return ResponseEntity.status(200).body(paymentService.processPayment(processPaymentRequest));
    }

}

package com.payment.payment_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.payment_service.enums.PaymentStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProcessPaymentResponse {
    
    @JsonProperty("transaction-id")
    Long transactionId;

    @JsonProperty("payment-status")
    PaymentStatus paymentStatus;
}

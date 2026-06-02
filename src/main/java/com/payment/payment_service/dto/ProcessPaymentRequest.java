package com.payment.payment_service.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.payment.payment_service.enums.PaymentMode;
import com.payment.payment_service.enums.TransactionType;

import jakarta.validation.constraints.NotNull;
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
public class ProcessPaymentRequest {
    
    @NotNull
    @JsonProperty("transaction-id")
    Long transactionId;

    @NotNull
    BigDecimal amount;

    @JsonProperty("payment-mode")
    @NotNull
    PaymentMode paymentMode;

    @NotNull
    @JsonProperty("transaction-type")
    TransactionType transactionType;
}

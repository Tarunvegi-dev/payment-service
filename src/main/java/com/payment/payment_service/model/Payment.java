package com.payment.payment_service.model;

import java.math.BigDecimal;
import java.time.Instant;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.payment.payment_service.enums.PaymentMode;
import com.payment.payment_service.enums.PaymentStatus;
import com.payment.payment_service.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payments")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payment {
    
    @Id
    @Column(name = "transaction_id", nullable = false, unique = true)
    Long transactionId;

    @Column(name = "amount", nullable = false, precision = 19, scale = 2)
    BigDecimal amount;

    @Column(name = "payment_mode")
    @Enumerated(EnumType.STRING)
    PaymentMode paymentMode;

    @Column(name = "transaction_type")
    @Enumerated(EnumType.STRING)
    TransactionType transactionType;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    Instant updatedAt;

    @Column(name = "payment_status", nullable = false)
    @Enumerated(EnumType.STRING)
    PaymentStatus paymentStatus;
}

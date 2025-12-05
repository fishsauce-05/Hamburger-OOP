package com.parking.strategy.payment;

import com.parking.model.Payment;
import com.parking.model.PaymentStatus;

import java.time.Instant;
import java.util.UUID;

public class CashPaymentStrategy implements PaymentStrategy {
    @Override
    public Payment processPayment(String ticketId, double amount, String method) {
        // Simulate immediate success
        Payment p = new Payment(UUID.randomUUID().toString(), ticketId, amount, "CASH", PaymentStatus.PAID, Instant.now());
        return p;
    }
}

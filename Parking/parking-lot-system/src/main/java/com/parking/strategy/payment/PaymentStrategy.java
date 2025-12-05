package com.parking.strategy.payment;

import com.parking.model.Payment;

public interface PaymentStrategy {
    Payment processPayment(String ticketId, double amount, String method) throws Exception;
}

package com.parking.service;

import com.parking.model.Payment;

public interface ExitService {
    Payment handleExit(String ticketId, com.parking.strategy.payment.PaymentStrategy paymentStrategy) throws Exception;
}

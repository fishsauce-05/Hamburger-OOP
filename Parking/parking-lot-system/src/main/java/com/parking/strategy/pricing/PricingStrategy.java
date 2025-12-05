package com.parking.strategy.pricing;

import com.parking.model.Ticket;

public interface PricingStrategy {
    double calculateFee(Ticket ticket);
}

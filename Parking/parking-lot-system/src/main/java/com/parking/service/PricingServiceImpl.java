package com.parking.service;

import com.parking.model.Ticket;
import com.parking.strategy.pricing.PricingStrategy;

public class PricingServiceImpl implements PricingService {
    private final PricingStrategy pricingStrategy;
    public PricingServiceImpl(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }
    @Override
    public double calculate(Ticket ticket) {
        return pricingStrategy.calculateFee(ticket);
    }
}

package com.parking.strategy.pricing;

import com.parking.model.Ticket;

import java.time.Duration;
import java.time.Instant;

public class HourlyPricingStrategy implements PricingStrategy {
    private final double hourlyRate;

    public HourlyPricingStrategy(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double calculateFee(Ticket ticket) {
        Instant end = ticket.getExitTime() != null ? ticket.getExitTime() : Instant.now();
        long minutes = Duration.between(ticket.getEntryTime(), end).toMinutes();
        long hours = Math.max(1, (minutes + 59) / 60); // round up, min 1 hour
        return hours * hourlyRate;
    }
}

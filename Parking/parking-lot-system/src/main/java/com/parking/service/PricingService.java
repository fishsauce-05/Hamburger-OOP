package com.parking.service;

import com.parking.model.Ticket;

public interface PricingService {
    double calculate(Ticket ticket);
}

package com.parking.service;

import com.parking.model.*;
import com.parking.repository.InMemoryRepositories;
import com.parking.repository.InMemoryRepositories.PaymentRepository;
import com.parking.repository.InMemoryRepositories.ParkingSpotRepository;
import com.parking.repository.InMemoryRepositories.TicketRepository;
import com.parking.strategy.payment.PaymentStrategy;

import java.time.Instant;
import java.util.Optional;

public class ExitServiceImpl implements ExitService {
    private final TicketRepository ticketRepo;
    private final ParkingSpotRepository spotRepo;
    private final PaymentRepository paymentRepo;
    private final PricingService pricingService;

    public ExitServiceImpl(TicketRepository ticketRepo, ParkingSpotRepository spotRepo,
                           PaymentRepository paymentRepo, PricingService pricingService) {
        this.ticketRepo = ticketRepo;
        this.spotRepo = spotRepo;
        this.paymentRepo = paymentRepo;
        this.pricingService = pricingService;
    }

    @Override
    public Payment handleExit(String ticketId, PaymentStrategy paymentStrategy) throws Exception {
        Ticket ticket = ticketRepo.findById(ticketId);
        if (ticket == null) throw new Exception("Ticket not found: " + ticketId);

        if (ticket.getTicketType() == TicketType.MONTHLY_SUBSCRIPTION) {
            // monthly subscription often has pre-paid, ensure active
            ticket.markExit(Instant.now(), "EXIT-GATE");
            ticket.setAmountDue(0.0);
            ticket.setPaymentStatus(PaymentStatus.PAID);
            ticketRepo.save(ticket);
            // free spot if assigned
            if (ticket.getAssignedSpotId() != null) {
                var s = spotRepo.findById(ticket.getAssignedSpotId());
                if (s != null) {
                    s.release();
                    spotRepo.save(s);
                }
            }
            Payment p = paymentStrategy.processPayment(ticketId, 0.0, "NONE");
            paymentRepo.save(p);
            return p;
        }

        // normal one-time ticket
        ticket.markExit(Instant.now(), "EXIT-GATE");
        double amount = pricingService.calculate(ticket);
        ticket.setAmountDue(amount);

        Payment p = paymentStrategy.processPayment(ticketId, amount, "CASH");
        if (p.getStatus() == PaymentStatus.PAID) {
            ticket.setPaymentStatus(PaymentStatus.PAID);
            slotReleaseIfAssigned(ticket);
        } else {
            ticket.setPaymentStatus(PaymentStatus.FAILED);
        }
        ticketRepo.save(ticket);
        paymentRepo.save(p);
        return p;
    }

    private void slotReleaseIfAssigned(Ticket ticket) {
        if (ticket.getAssignedSpotId() != null) {
            ParkingSpot s = spotRepo.findById(ticket.getAssignedSpotId());
            if (s != null) {
                s.release();
                spotRepo.save(s);
            }
        }
    }
}

package com.parking.model;

import java.time.Instant;

public class Payment {
    private final String id;
    private final String ticketId;
    private final double amount;
    private final String method;
    private PaymentStatus status;
    private final Instant paidAt;

    public Payment(String id, String ticketId, double amount, String method, PaymentStatus status, Instant paidAt) {
        this.id = id;
        this.ticketId = ticketId;
        this.amount = amount;
        this.method = method;
        this.status = status;
        this.paidAt = paidAt;
    }
    public String getId() { return id; }
    public String getTicketId() { return ticketId; }
    public double getAmount() { return amount; }
    public String getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public Instant getPaidAt() { return paidAt; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}

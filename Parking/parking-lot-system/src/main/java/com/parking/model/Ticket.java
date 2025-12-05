package com.parking.model;

import java.time.Duration;
import java.time.Instant;

public class Ticket {
    private final String id;
    private final TicketType ticketType;
    private final String vehicleId;
    private final VehicleType vehicleType;
    private final Instant entryTime;
    private Instant exitTime;
    private final String entryGateId;
    private String exitGateId;
    private String assignedSpotId;
    private boolean closed = false;
    private double amountDue = 0.0;
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;
    private String subscriptionId;

    public Ticket(String id, TicketType ticketType, String vehicleId, VehicleType vehicleType, Instant entryTime, String entryGateId) {
        this.id = id;
        this.ticketType = ticketType;
        this.vehicleId = vehicleId;
        this.vehicleType = vehicleType;
        this.entryTime = entryTime;
        this.entryGateId = entryGateId;
    }

    public String getId() { return id; }
    public TicketType getTicketType() { return ticketType; }
    public String getVehicleId() { return vehicleId; }
    public VehicleType getVehicleType() { return vehicleType; }
    public Instant getEntryTime() { return entryTime; }
    public Instant getExitTime() { return exitTime; }
    public String getEntryGateId() { return entryGateId; }
    public String getExitGateId() { return exitGateId; }
    public String getAssignedSpotId() { return assignedSpotId; }
    public boolean isClosed() { return closed; }
    public double getAmountDue() { return amountDue; }
    public PaymentStatus getPaymentStatus() { return paymentStatus; }
    public String getSubscriptionId() { return subscriptionId; }

    public void assignSpot(String spotId) { this.assignedSpotId = spotId; }
    public void markExit(Instant at, String exitGate) {
        this.exitTime = at;
        this.exitGateId = exitGate;
        this.closed = true;
    }
    public long getDurationMinutes() {
        Instant end = exitTime != null ? exitTime : Instant.now();
        return Duration.between(entryTime, end).toMinutes();
    }
    public void setAmountDue(double amount) { this.amountDue = amount; }
    public void setPaymentStatus(PaymentStatus status) { this.paymentStatus = status; }
    public void setSubscriptionId(String id) { this.subscriptionId = id; }
}

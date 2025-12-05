package com.parking.model;

import java.time.Instant;

public class MonthlySubscription {
    private final String id;
    private final String customerId;
    private final VehicleType vehicleType;
    private final Instant startDate;
    private final Instant endDate;
    private final String assignedSpotId;

    public MonthlySubscription(String id, String customerId, VehicleType vehicleType, Instant startDate, Instant endDate, String assignedSpotId) {
        this.id = id;
        this.customerId = customerId;
        this.vehicleType = vehicleType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.assignedSpotId = assignedSpotId;
    }
    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public VehicleType getVehicleType() { return vehicleType; }
    public Instant getStartDate() { return startDate; }
    public Instant getEndDate() { return endDate; }
    public String getAssignedSpotId() { return assignedSpotId; }
    public boolean isActive() {
        Instant now = Instant.now();
        return !now.isBefore(startDate) && now.isBefore(endDate);
    }
}

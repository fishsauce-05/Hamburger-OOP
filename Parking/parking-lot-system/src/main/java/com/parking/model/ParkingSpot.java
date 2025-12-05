package com.parking.model;

import java.util.Objects;

public class ParkingSpot {
    private final String id;
    private final String levelId;
    private final SpotType type;
    private SpotState state;
    private final String spotNumber;
    private String currentTicketId; // nullable
    private String reservedForSubscriptionId; // nullable

    public ParkingSpot(String id, String levelId, SpotType type, String spotNumber) {
        this.id = id;
        this.levelId = levelId;
        this.type = type;
        this.spotNumber = spotNumber;
        this.state = SpotState.FREE;
    }

    public String getId() { return id; }
    public String getLevelId() { return levelId; }
    public SpotType getType() { return type; }
    public SpotState getState() { return state; }
    public String getSpotNumber() { return spotNumber; }
    public String getCurrentTicketId() { return currentTicketId; }
    public String getReservedForSubscriptionId() { return reservedForSubscriptionId; }

    public synchronized boolean occupy(String ticketId) {
        if (state == SpotState.FREE || state == SpotState.RESERVED) {
            this.currentTicketId = ticketId;
            this.state = SpotState.OCCUPIED;
            return true;
        }
        return false;
    }
    public synchronized boolean release() {
        if (state == SpotState.OCCUPIED) {
            this.currentTicketId = null;
            this.state = SpotState.FREE;
            return true;
        }
        return false;
    }
    public synchronized void reserve(String subscriptionId) {
        this.reservedForSubscriptionId = subscriptionId;
        if (state == SpotState.FREE) this.state = SpotState.RESERVED;
    }
    public boolean isAvailableFor(VehicleType vtype) {
        // simplified mapping
        return this.state == SpotState.FREE || this.state == SpotState.RESERVED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ParkingSpot)) return false;
        ParkingSpot that = (ParkingSpot) o;
        return Objects.equals(id, that.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

package com.parking.model;

public class Vehicle {
    private final String id;
    private final String licensePlate;
    private final VehicleType type;
    private final String ownerId;

    public Vehicle(String id, String licensePlate, VehicleType type, String ownerId) {
        this.id = id;
        this.licensePlate = licensePlate;
        this.type = type;
        this.ownerId = ownerId;
    }
    public String getId() { return id; }
    public String getLicensePlate() { return licensePlate; }
    public VehicleType getType() { return type; }
    public String getOwnerId() { return ownerId; }
}

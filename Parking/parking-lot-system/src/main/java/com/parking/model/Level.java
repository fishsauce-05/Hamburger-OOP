package com.parking.model;

import java.util.ArrayList;
import java.util.List;

public class Level {
    private final String id;
    private final String name;
    private final int floorNumber;
    private final List<ParkingSpot> spots = new ArrayList<>();

    public Level(String id, String name, int floorNumber) {
        this.id = id;
        this.name = name;
        this.floorNumber = floorNumber;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public int getFloorNumber() { return floorNumber; }
    public List<ParkingSpot> getSpots() { return spots; }
    public void addSpot(ParkingSpot s) { spots.add(s); }
}

package com.parking.model;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final String id;
    private final String name;
    private final List<Level> levels = new ArrayList<>();

    public ParkingLot(String id, String name) {
        this.id = id;
        this.name = name;
    }
    public String getId() { return id; }
    public String getName() { return name; }
    public List<Level> getLevels() { return levels; }
    public void addLevel(Level level) { levels.add(level); }
}

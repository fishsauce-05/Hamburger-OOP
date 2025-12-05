package com.parking.strategy.spot;

import com.parking.model.ParkingSpot;
import com.parking.model.Vehicle;

import java.util.List;
import java.util.Optional;

public class FirstAvailableStrategy implements SpotAssignmentStrategy {
    @Override
    public Optional<ParkingSpot> assignSpot(Vehicle vehicle, List<ParkingSpot> candidates) {
        return candidates.stream().filter(s -> s.getState().name().equals("FREE") || s.getState().name().equals("RESERVED")).findFirst();
    }
}

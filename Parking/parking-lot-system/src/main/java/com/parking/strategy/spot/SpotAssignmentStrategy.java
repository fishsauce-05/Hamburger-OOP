package com.parking.strategy.spot;

import com.parking.model.ParkingSpot;
import com.parking.model.Vehicle;

import java.util.List;
import java.util.Optional;

public interface SpotAssignmentStrategy {
    Optional<ParkingSpot> assignSpot(Vehicle vehicle, List<ParkingSpot> candidates);
}

package com.parking.model;

import java.util.*;

public class Floor {
	private String id, name;
	private int floorNumber;
	private List<ParkingSpot> spots = new ArrayList<>();

	public Floor(String id, String name, int floorNumber) {
		this.id = id;
		this.name = name;
		this.floorNumber = floorNumber;
	}
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}

	public int getFloorNumber() {
		return this.floorNumber;
	}
	public List<ParkingSpot> getSpots() {
		return this.spots;
	}
	public void addSpot(ParkingSpot ps) {
		spots.add(ps);
	}
}
package com.parking.model;

import java.util.*;

public class ParkingLot {
	private String id, name;
	private List<Floor> floors = new ArrayList<>();
	
	public ParkingLot(String id, String name) {
		this.id = id;
		this.name = name;
	}
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public List<Floor> getFloors() {
		return this.floors;
	}
	public void addFloor(Floor floor) {
		floors.add(floor);
	}
}
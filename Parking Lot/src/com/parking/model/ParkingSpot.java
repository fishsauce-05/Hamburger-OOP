package com.parking.model;

import java.util.*;

public class ParkingSpot {
	private String id, floorId;
	private SpotType type;
	private SpotState state;
	private String spotNumber, currentTicketId, fix4DangKyId;
	
	public ParkingSpot(String id, String floorId, SpotType type, String spotNumber) {
		this.id = id;
		this.floorId = floorId;
		this.type = type;
		this.spotNumber = spotNumber;
		this.state = SpotState.FREE;
	}

	public String getId() {
		return this.id;
	}
	public String getFloorId() {
		return this.floorId;
	}
	public SpotType getType() {
		return this.type;
	}
	public SpotState getState() {
		return this.state;
	}
	public String getSpotNumber() {
		return this.spotNumber;
	}
	public String getCurrentTicketId() {
		return this.currentTicketId;
	}

	public boolean occupy(String ticketId) {
		if (state == SpotState.OCCUPIED || state == SpotState.OUT_OF_SERVICE) return false;
		this.currentTicketId = ticketId;
		this.state = SpotState.OCCUPIED;
		return true;
	}
	public boolean out() {
		if (state != SpotState.OCCUPIED) return false;
		this.currentTicketId = null;
		this.state = SpotState.FREE;
		return true;
	}
	public void fix(String dkyId) {
		this.fix4DangKyId = dkyId;
		if (state == SpotState.FREE) this.state = SpotState.FIX;
	}
	public boolean isAvailable() {
		return this.state == SpotState.FREE || this.state == SpotState.FIX;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ParkingSpot)) return false;
		ParkingSpot other = (ParkingSpot)obj;
		return Objects.equals(this.id, other.id);
	}
}
package com.parking.model;

import java.time.*;

public class MonthlyDangKy {
	private String id, customerId;
	private VehicleType vehicleType;
	private Instant stDate, enDate;
	private String appliedSpotId;

	public MonthlySubscription(String id, String customerId, VehicleType vehicleType, Instant stDate, Instant enDate, String appliedSpotId) {
		this.id = id;
		this.customerId = customerId;
		this.vehicleType = vehicleType;
		this.stDate = stDate;
		this.enDate = enDate;
		this.appliedSpotId = appliedSpotId;
	}
	public String getId() {
		return this.id;
	}
	public String getCustomerId() {
		return this.customerId;
	}
	public VehicleType getVehicleType() {
		return this.vehicleType;
	}
	public Instant getStartDate() {
		return this.stDate;
	}
	public Instant getEndDate() {
		return this.enDate;
	}
	public String getAppliedSpotId() {
		return this.appliedSpotId;
	}
	public boolean isActive() {
		Instant current = Instant.now();
		return !current.isBefore(stDate) && current.isBefore(enDate);
	}
}
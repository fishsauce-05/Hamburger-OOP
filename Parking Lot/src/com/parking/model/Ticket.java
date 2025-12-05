package com.parking.model;

import java.util.*;

public class Ticket {
	private String id;
	private TicketType ticketType;
	private String vehicleId;
	private VehicleType vehicleType;
	private Instant entryTime, exitTime;
	private String entryGateId, appliedSpotId;
	private boolean closed = false;
	private double amount = 0.0;
	private PaymentStatus paymentStatus = PaymentStatus.PENDING;
	private String dkyId;

	public Ticket(String id, TicketType ticketType, String vehicleId, VehicleType vehicleType, Instant entryTime, String entryGateId) {
		this.id = id;
		this.ticketType = ticketType;
		this.vehicleId = vehicleId;
		this.vehicleType = vehicleType;
		this.entryTime = entryTime;
		this.entryGateId = entryGateId;
	}
	
	//getter
	public String getId() {
		return this.id;
	}
	public TicketType getTicketType() {
		return this.ticketType;
	}
	public String getVehicleId() {
		return this.vehicleId;
	}
	public VehicleType getVehicleType() {
		return this.vehicleType;
	}
	public Instant getEntryTime() {
		return this.entryTime;
	}
	public Instant getExitTime() {
		return this.exitTime;
	}
	public String getEntryGateId() {
		return this.entryGateId;
	}
	public String getAppliedSpotId() {
		return this.appliedSpotId;
	}
	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}
	public String getDangKyId() {
		return this.dkyId;
	}
	public long getDurationMinutes() {
		Instant end = (exitTime != null ? exitTime : Instant.now());
		return Duration.between(entryTime, end).toMinutes();
	}

	public boolean isClosed() {
		return this.closed;
	}

	//setter
	public void markSpot(String spotId) {
		this.appliedSpotId = spotId;
	}
	public void markExit(Instant exitTime, String exitGateId) {
		this.exitTime = exitTime;
		this.exitGateId = exitGateId;
		this.closed = true;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus){
		this.paymentStatus = paymentStatus;
	}
	public void setDangKyId(String dkyId) {
		this.dkyId = dkyId;
	}
}
package com.parking.model;

import java.time.*;

public class Payment {
	private String id, ticketId;
 	private double amount;
	private String method;
	private PaymentStatus status;
	private Instant paidAt;

	public Payment(String id, String ticketId, double amount, String method, PaymentStatus status, Instant paidAt) {
		this.id = id;
		this.ticketId = ticketId;
		this.amount = amount;
		this.method - method;
		this.status = status;
		this.paidAt = paidAt;
	}
	
	public String getId() { 
		return this.id;
	}
	public String getTicketId() {
		return this.ticketId;
	}
	public double getAmount() {
		return this.amount;
	}
	public PaymentStatus getPaymentStatus() {
		return this.status;
	}
	public Instant getPaidAt() {
		return this.paidAt;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
}
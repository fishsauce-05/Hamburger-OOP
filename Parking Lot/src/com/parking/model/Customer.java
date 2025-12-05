package com.parking.model;

public class Customer {
	private String id;
	private String name;
	private String contact;
	
	public Customer(String id, String name, String contact) {
		this.id = id;
		this.name = name;
		this.contact = contact;
	}
	public String getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getContact() {
		return this.contact;
	}
}
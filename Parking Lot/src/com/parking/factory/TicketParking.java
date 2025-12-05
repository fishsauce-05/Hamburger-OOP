package com.parking.factory;

import com.parking.model.*;
import java.util.*;
import java.time.*;

public class TicketParking implements TicketFactory {
	static int ii_once = 0;
	static int ii_monthly = 0;
	@Override
	public Ticket createOnceTicket(Vehicle vehicle, String entryGateId) {
		ii_once++;
		String id = String.format("OT%05d", ii_once);
		Ticket ticket = new Ticket(id, TicketType.ONCE, vehicle.getId(), Instant.now(), entryGateId);
		return ticket;
	}
	
	@Override
	public Ticket createMonthlyTicket(MonthlyDangKy dky, Vehicle vehicle, String entryGateId) {
		ii_monthly++;
		String id = String.format("EM%05d", ii_monthly);
		Ticket ticket = new Ticket(id, TicketType.MONTHLY, vehicle.getId(), vehicle.getType(), Instant.now(), entryGateId);
		ticket.setDangKyId(dky.getId());
		ticket.setAmountDue(0.0);
		ticket.setPaymentStatus(PaymentStatus.PAID);
	}
}
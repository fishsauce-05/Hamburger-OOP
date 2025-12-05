package com.parking.factory;

import com.parking.model.*;

public interface TicketFactory {
	Ticket createOnceTicket(Vehicle vehicle, String entryGateId);
        Ticket createMonthlyTicket(MonthlySubscription subscription, Vehicle vehicle, String entryGateId);
}
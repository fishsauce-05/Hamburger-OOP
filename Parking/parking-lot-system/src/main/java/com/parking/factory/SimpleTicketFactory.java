package com.parking.factory;

import com.parking.model.*;

import java.time.*;
import java.util.*;

public class SimpleTicketFactory implements TicketFactory {
    @Override
    public Ticket createOneTimeTicket(Vehicle vehicle, String entryGateId) {
        String id = UUID.randomUUID().toString();
        Ticket t = new Ticket(id, TicketType.ONE_TIME, vehicle.getId(), vehicle.getType(), Instant.now(), entryGateId);
        return t;
    }

    @Override
    public Ticket createMonthlyTicket(MonthlySubscription subscription, Vehicle vehicle, String entryGateId) {
        String id = UUID.randomUUID().toString();
        Ticket t = new Ticket(id, TicketType.MONTHLY_SUBSCRIPTION, vehicle.getId(), vehicle.getType(), Instant.now(), entryGateId);
        t.setSubscriptionId(subscription.getId());
        // monthly might not charge at exit
        t.setAmountDue(0.0);
        t.setPaymentStatus(PaymentStatus.PAID);
        return t;
    }
}

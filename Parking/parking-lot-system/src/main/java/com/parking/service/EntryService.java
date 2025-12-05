package com.parking.service;

import com.parking.model.Ticket;
import com.parking.model.Vehicle;

public interface EntryService {
    Ticket handleEntry(Vehicle vehicle, String entryGateId) throws Exception;
}

package com.parking.service;

import com.parking.factory.TicketFactory;
import com.parking.model.*;
import com.parking.repository.InMemoryRepositories;
import com.parking.repository.InMemoryRepositories.ParkingSpotRepository;
import com.parking.repository.InMemoryRepositories.TicketRepository;
import com.parking.strategy.spot.SpotAssignmentStrategy;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntryServiceImpl implements EntryService {
    private final ParkingSpotRepository spotRepo;
    private final TicketRepository ticketRepo;
    private final TicketFactory ticketFactory;
    private final SpotAssignmentStrategy assignmentStrategy;
    private final InMemoryRepositories.ParkingLotRepository lotRepo;

    public EntryServiceImpl(ParkingSpotRepository spotRepo, TicketRepository ticketRepo, TicketFactory ticketFactory,
                            SpotAssignmentStrategy assignmentStrategy, InMemoryRepositories.ParkingLotRepository lotRepo) {
        this.spotRepo = spotRepo;
        this.ticketRepo = ticketRepo;
        this.ticketFactory = ticketFactory;
        this.assignmentStrategy = assignmentStrategy;
        this.lotRepo = lotRepo;
    }

    @Override
    public Ticket handleEntry(Vehicle vehicle, String entryGateId) throws Exception {
        // Simple logic: find any compatible spot
        List<ParkingSpot> candidates = spotRepo.findAvailableForVehicle(vehicle.getType());
        if (candidates.isEmpty()) throw new Exception("No available spot for vehicle type: " + vehicle.getType());

        Optional<ParkingSpot> chosen = assignmentStrategy.assignSpot(vehicle, candidates);
        if (chosen.isEmpty()) throw new Exception("No spot assigned");

        ParkingSpot spot = chosen.get();
        Ticket ticket = ticketFactory.createOneTimeTicket(vehicle, entryGateId);
        ticket.assignSpot(spot.getId());

        // Occupy spot
        boolean occupied = spot.occupy(ticket.getId());
        if (!occupied) throw new Exception("Failed to occupy spot");

        // persist
        spotRepo.save(spot);
        ticketRepo.save(ticket);
        return ticket;
    }
}

package com.parking;

import com.parking.config.DataLoader;
import com.parking.factory.SimpleTicketFactory;
import com.parking.model.*;
import com.parking.repository.*;
import com.parking.service.*;
import com.parking.strategy.payment.CashPaymentStrategy;
import com.parking.strategy.pricing.HourlyPricingStrategy;
import com.parking.strategy.spot.FirstAvailableStrategy;

import java.time.Instant;
import java.util.UUID;

public class Application {
    public static void main(String[] args) {
        System.out.println("Starting Parking Lot System demo...");

        // Repositories (in-memory)
        InMemoryRepositories repos = new InMemoryRepositories();

        // Utils / strategies / factories
        var pricingStrategy = new HourlyPricingStrategy(5.0); // $5 per hour
        var paymentStrategy = new CashPaymentStrategy();
        var spotStrategy = new FirstAvailableStrategy();

        var ticketFactory = new SimpleTicketFactory();

        // Services
        var pricingService = new PricingServiceImpl(pricingStrategy);
        var entryService = new EntryServiceImpl(repos.parkingSpotRepository, repos.ticketRepository,
                ticketFactory, spotStrategy, repos.parkingLotRepository);
        var exitService = new ExitServiceImpl(repos.ticketRepository, repos.parkingSpotRepository,
                repos.paymentRepository, pricingService);

        // Load sample data
        DataLoader.loadSampleParkingLot(repos.parkingLotRepository, repos.parkingSpotRepository);

        // Simulate vehicle entry
        Vehicle vehicle = new Vehicle(UUID.randomUUID().toString(), "ABC-123", VehicleType.CAR, null);
        try {
            Ticket ticket = entryService.handleEntry(vehicle, "ENTRY-A");
            System.out.println("Ticket issued: " + ticket.getId() + " entryTime=" + ticket.getEntryTime());

            // Simulate some time passing
            Thread.sleep(1500); // 1.5s ~ minimal time; pricing uses hours so it will be small

            // Simulate exit and payment
            var payment = exitService.handleExit(ticket.getId(), paymentStrategy);
            System.out.println("Payment processed: " + payment.getStatus() + " amount=" + payment.getAmount());

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Monthly subscription demo
        Customer cust = new Customer(UUID.randomUUID().toString(), "Alice", "alice@example.com");
        repos.customerRepository.save(cust);
        MonthlySubscription sub = new MonthlySubscription(UUID.randomUUID().toString(),
                cust.getId(), VehicleType.CAR, Instant.now(), Instant.now().plusSeconds(60 * 60 * 24 * 30), null);
        repos.subscriptionRepository.save(sub);

        Vehicle v2 = new Vehicle(UUID.randomUUID().toString(), "MONTHLY-01", VehicleType.CAR, cust.getId());
        try {
            // create monthly ticket manually via factory
            Ticket monthlyTicket = ticketFactory.createMonthlyTicket(sub, v2, "ENTRY-B");
            repos.ticketRepository.save(monthlyTicket);
            System.out.println("Monthly ticket allowed: " + monthlyTicket.getId());

            // Exit monthly
            var payment2 = exitService.handleExit(monthlyTicket.getId(), paymentStrategy);
            System.out.println("Monthly exit payment (should be 0 or minimal): " + payment2.getAmount());

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Demo finished.");
    }
}

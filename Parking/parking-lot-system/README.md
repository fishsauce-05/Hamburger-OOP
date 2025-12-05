# Parking Lot Management System (Java)

A sample Java project that implements a Parking Lot Management System focusing on core OOP, SOLID principles and common design patterns (Factory, Strategy, Repository, Observer). This project is intentionally lightweight (no Spring Boot, no DB) and uses in-memory repositories so you can run it as a simple Java application.

What is included:
- Domain model (ParkingLot, Level, ParkingSpot, Ticket, Vehicle, Subscription, Payment)
- In-memory repositories (thread-safe)
- Services: EntryService, ExitService, PricingService, SubscriptionService
- Strategies: PricingStrategy, PaymentStrategy, SpotAssignmentStrategy
- Factories: TicketFactory
- Sample data loader and an Application main to simulate entry/exit

How to run:
1. Build with Maven:
   mvn -q -DskipTests package
2. Run:
   java -jar target/parking-lot-system-1.0.jar

Or run from your IDE with main class `com.parking.Application`.

Notes:
- This project aims to be a clean, extensible starting point. Replace repositories with DB-backed implementations as needed.
- Time handling uses java.time.Instant and a TimeProvider util for testability.

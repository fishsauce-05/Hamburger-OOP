package com.parking.repository;

import com.parking.model.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Collection of simple in-memory repository implementations used by the demo.
 */
public class InMemoryRepositories {
    public final ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
    public final ParkingSpotRepository parkingSpotRepository = new ParkingSpotRepository();
    public final TicketRepository ticketRepository = new TicketRepository();
    public final SubscriptionRepository subscriptionRepository = new SubscriptionRepository();
    public final PaymentRepository paymentRepository = new PaymentRepository();
    public final CustomerRepository customerRepository = new CustomerRepository();

    public static class ParkingLotRepository implements Repository<ParkingLot, String> {
        private final Map<String, ParkingLot> map = new ConcurrentHashMap<>();
        public ParkingLot save(ParkingLot p) { map.put(p.getId(), p); return p; }
        public ParkingLot findById(String id) { return map.get(id); }
        public List<ParkingLot> findAll() { return new ArrayList<>(map.values()); }
        public void delete(String id) { map.remove(id); }
    }

    public static class ParkingSpotRepository implements Repository<ParkingSpot, String> {
        private final Map<String, ParkingSpot> map = new ConcurrentHashMap<>();
        public ParkingSpot save(ParkingSpot s) { map.put(s.getId(), s); return s; }
        public ParkingSpot findById(String id) { return map.get(id); }
        public List<ParkingSpot> findAll() { return new ArrayList<>(map.values()); }
        public void delete(String id) { map.remove(id); }

        public List<ParkingSpot> findByLevelAndType(String levelId, SpotType type) {
            return map.values().stream()
                    .filter(s -> s.getLevelId().equals(levelId) && s.getType() == type)
                    .collect(Collectors.toList());
        }
        public List<ParkingSpot> findAvailableByType(SpotType type) {
            return map.values().stream()
                    .filter(s -> s.getType() == type && (s.getState() == SpotState.FREE || s.getState() == SpotState.RESERVED))
                    .collect(Collectors.toList());
        }
        public List<ParkingSpot> findAvailableForVehicle(VehicleType vtype) {
            // simplified mapping: CAR -> REGULAR, BIKE -> REGULAR, TRUCK -> RESERVED
            return map.values().stream()
                    .filter(s -> s.isAvailableFor(vtype))
                    .collect(Collectors.toList());
        }
    }

    public static class TicketRepository implements Repository<Ticket, String> {
        private final Map<String, Ticket> map = new ConcurrentHashMap<>();
        public Ticket save(Ticket t) { map.put(t.getId(), t); return t; }
        public Ticket findById(String id) { return map.get(id); }
        public List<Ticket> findAll() { return new ArrayList<>(map.values()); }
        public void delete(String id) { map.remove(id); }
        public Optional<Ticket> findActiveTicketByVehicle(String vehicleId) {
            return map.values().stream().filter(t -> !t.isClosed() && t.getVehicleId().equals(vehicleId)).findFirst();
        }
    }

    public static class SubscriptionRepository implements Repository<MonthlySubscription, String> {
        private final Map<String, MonthlySubscription> map = new ConcurrentHashMap<>();
        public MonthlySubscription save(MonthlySubscription s) { map.put(s.getId(), s); return s; }
        public MonthlySubscription findById(String id) { return map.get(id); }
        public List<MonthlySubscription> findAll() { return new ArrayList<>(map.values()); }
        public void delete(String id) { map.remove(id); }
        public List<MonthlySubscription> findActiveByCustomer(String customerId) {
            return map.values().stream().filter(s -> s.getCustomerId().equals(customerId) && s.isActive()).collect(Collectors.toList());
        }
    }

    public static class PaymentRepository implements Repository<Payment, String> {
        private final Map<String, Payment> map = new ConcurrentHashMap<>();
        public Payment save(Payment p) { map.put(p.getId(), p); return p; }
        public Payment findById(String id) { return map.get(id); }
        public List<Payment> findAll() { return new ArrayList<>(map.values()); }
        public void delete(String id) { map.remove(id); }
    }

    public static class CustomerRepository implements Repository<Customer, String> {
        private final Map<String, Customer> map = new ConcurrentHashMap<>();
        public Customer save(Customer c) { map.put(c.getId(), c); return c; }
        public Customer findById(String id) { return map.get(id); }
        public List<Customer> findAll() { return new ArrayList<>(map.values()); }
        public void delete(String id) { map.remove(id); }
    }
}

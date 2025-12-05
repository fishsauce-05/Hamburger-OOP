# Parking Lot Management System — Thiết kế chính (Java, OOP, SOLID, Design Patterns)

Phiên bản: 1.0  
Tác giả: (thiết kế bởi AI, triển khai bởi developer)  
Mục tiêu: Tài liệu này mô tả cấu trúc dự án, ERD, các class chính, interface, pattern được dùng, và các luồng chính (Entry / Exit / Monthly). Dự án dùng Java, không chứa UI. Tập trung vào OOP, SOLID, dễ mở rộng.

Mục lục
- Tổng quan kiến trúc
- Cây thư mục / package
- ERD (entities & relationships)
- Các design pattern áp dụng
- Mô tả chi tiết các class (package, attributes, methods) — signatures Java
- Các use-case flows (sequence ngắn)
- Gợi ý triển khai & mở rộng

---

## 1. Tổng quan kiến trúc

Kiến trúc 3-layer (Presentation/Controller - Service - Repository) + Domain Model.  
Các thành phần chính:
- domain/models: Entities (ParkingLot, Level, ParkingSpot, Ticket, Vehicle, Subscription, Payment...)
- repository: interface repository (in-memory / file / DB implementations)
- service: business logic (EntryService, ExitService, PricingService, SubscriptionService, MonitoringService)
- controller: cửa vào/ra (EntryGateController/ExitGateController) — giả lập không có UI
- strategy/factory: PricingStrategy, PaymentStrategy, TicketFactory, SpotAssignmentStrategy
- monitoring/observer: OccupancyMonitor, Events

Nguyên tắc: Mỗi lớp có single responsibility; tất cả phụ thuộc vào abstraction (interfaces).

---

## 2. Cây thư mục / packages (gợi ý)

- src/main/java/com/parking/
  - model/           (domain entities)
  - repository/      (interfaces + in-memory impl)
  - service/         (business services)
  - controller/      (gates / orchestrators)
  - dto/             (data transfer objects if cần)
  - factory/         (TicketFactory, VehicleFactory)
  - strategy/        (pricing, payment, spot assignment)
  - monitoring/      (observers, notifications)
  - exception/       (custom exceptions)
  - util/            (IdGenerator, TimeProvider)
  - config/          (app config, initial data loader)
- docs/
  - DESIGN.md (this file)
  - ERD.png (tùy thêm)

---

## 3. ERD (Entities & relationships) — ngôn ngữ tự nhiên

Các thực thể chính:

- ParkingLot (1) --- (N) Level  
- Level (1) --- (N) ParkingSpot  
- ParkingSpot (N) --- (0..1) Ticket (current active ticket)  
- Vehicle (1) --- (0..N) Ticket  
- Ticket (N) --- (0..1) Payment  
- MonthlySubscription (1) --- (1) Customer (Account)  
- MonthlySubscription (1) --- (1) ParkingSpot (reserved spot) [optional]  
- EntryGate / ExitGate tham chiếu tới ParkingLot (hoặc Level) — dùng để log events.

Mô tả rút gọn quan hệ:
- Một ParkingLot có nhiều Level; mỗi Level có nhiều ParkingSpot.
- Một ParkingSpot có một SpotType (CAR/BIKE/TRUCK/HANDICAPPED/RESERVED/MONTHLY).
- Ticket đại diện cho lượt gửi (one-time) hoặc tham chiếu MonthlySubscription cho vé tháng.
- MonthlySubscription liên kết Customer, hạn bắt đầu/kết thúc, spot cố định (nếu gắn chỗ).
- Payment liên kết Ticket, ghi status & method.

---

## 4. Design Patterns áp dụng (mapping)

- Factory
  - TicketFactory: tạo ParkingTicket (OneTimeTicket hoặc MonthlyAccessToken).
  - VehicleFactory: tạo Vehicle theo type.
- Strategy
  - PricingStrategy: các chiến lược tính phí (FlatRateStrategy, HourlyStrategy, TieredStrategy, PeakHourStrategy).
  - PaymentStrategy: CashPaymentStrategy, CardPaymentStrategy (mô phỏng).
  - SpotAssignmentStrategy: FirstAvailableStrategy, NearestStrategy, ReservedFirstStrategy.
- Repository (DAO pattern)
  - interface Repository<T> + InMemoryRepository implementations.
- Singleton / Manager
  - ParkingLotManager (Singleton hoặc được spring-managed) — giữ trạng thái aggregated.
- State
  - ParkingSpotState: FREE, OCCUPIED, RESERVED, OUT_OF_SERVICE — có thể implement State pattern nếu cần hành vi phức tạp.
- Observer / Event
  - OccupancyMonitor đăng ký để nhận event khi spot thay đổi (để cảnh báo full / generate reports).
- Template Method / Chain of Responsibility (tuỳ chọn)
  - Payment processing pipeline (validate -> authorize -> capture). (có thể mở rộng sau)

---

## 5. Mô tả các class chính (Java signatures)

Phần dưới cung cấp các class / interface chính, theo package. Chỉ liệt kê attributes & phương thức công khai/quan trọng. (Không phải implement đầy đủ logic).

### 5.1 package com.parking.model

- Enums: VehicleType, SpotType, SpotState, TicketType, PaymentStatus
- ParkingLot, Level, ParkingSpot, Vehicle, Customer, Ticket, MonthlySubscription, Payment

### 5.2 package com.parking.repository

- Repository<T, ID>
- InMemory repository implementations

### 5.3 package com.parking.factory
- TicketFactory, SimpleTicketFactory

### 5.4 package com.parking.strategy.pricing
- PricingStrategy, HourlyPricingStrategy

### 5.5 package com.parking.strategy.payment
- PaymentStrategy, CashPaymentStrategy

### 5.6 package com.parking.strategy.spot
- SpotAssignmentStrategy, FirstAvailableStrategy

### 5.7 package com.parking.service
- EntryService, ExitService, PricingService and implementations

### 5.8 package com.parking.controller
- Entry/Exit controllers (not implemented in this demo)

### 5.9 package com.parking.monitoring
- OccupancyObserver, ParkingStatus (not implemented in this demo)

### 5.10 package com.parking.exception
- Custom exceptions

---

## 6. Luồng chính (Sequence, tóm tắt)

1) Entry (One-time visitor)
- Driver arrives at EntryGateController.requestEntry(vehicle, gateId)
- EntryService:
  - Validate vehicle type
  - Check available spots by SpotType compatible
  - If none => throw ParkingFullException (controller returns message)
  - Assign spot (SpotAssignmentStrategy)
  - Create Ticket via TicketFactory (ONE_TIME)
  - Mark spot OCCUPIED and link ticketId
  - Persist ticket (TicketRepository.save)
  - Open barrier (simulated) -> return ticket info

2) Inside (parking)
- System state updated: ParkingSpot.state = OCCUPIED
- MonitoringService observers notified

3) Exit & Payment
- Driver provides ticketId -> ExitGateController.requestExit()
- ExitService:
  - Retrieve Ticket, compute duration
  - PricingService.calculate(ticket) -> amount
  - Call PaymentStrategy.processPayment(ticketId, amount)
  - On success: mark payment status PAID, ticket.closed = true, set exitTime, free the spot
  - Notify monitors, return receipt

4) Monthly subscription (entry)
- MonthlySubscriber registered with SubscriptionService (assigned spot optional)
- When vehicle arrives, EntryService checks subscription validity:
  - If valid, treat as allowed without payment; may skip spot assignment if subscription has reserved spot; else may assign free spot of monthly type.
- On renew/expiry, admin tools notify.

---

## 7. Một ví dụ mapping classes -> pattern

- TicketFactory.createOneTimeTicket -> Factory
- PricingService depends on pricingStrategy -> Strategy
- PaymentService uses PaymentStrategy -> Strategy
- SpotAssignmentStrategy -> Strategy
- ParkingLotManager (aggregate) -> Singleton / Composition
- OccupancyMonitor implements Observer

---

## 8. Non-functional & concurrency notes

- Repositories must be thread-safe (ConcurrentHashMap) nếu multi-gate song song.
- Use TimeProvider (util) for testable time.
- ID generation via UUID or IdGenerator util.
- For production, repository interfaces map tới DB (JPA/Hibernate), nhưng hiện tại InMemory impl đủ.

---

## 9. Gợi ý tests & scenarios

- Unit tests:
  - EntryService: success assign, failure when full, monthly bypass.
  - ExitService: correct fee calculation for different strategies, payment success/failure flows.
  - SubscriptionService: create, expire, access control.
- Integration tests:
  - Simulate multiple entries/exits at concurrent gates to ensure spot counts remain consistent.

---

## 10. Ví dụ mapping và kết luận

Tài liệu mô tả cấu trúc và các thành phần chính. Dự án demo kèm theo main class để chạy thử. Bạn có thể tiếp tục bằng cách:
- Thêm unit tests
- Thêm REST API (Spring Boot)
- Thay repo in-memory bằng JPA/Hibernate

package com.parking.exception;

public class Exceptions {
    public static class ParkingFullException extends RuntimeException {
        public ParkingFullException(String msg) { super(msg); }
    }
    public static class TicketNotFoundException extends RuntimeException {
        public TicketNotFoundException(String msg) { super(msg); }
    }
    public static class PaymentException extends RuntimeException {
        public PaymentException(String msg) { super(msg); }
    }
}

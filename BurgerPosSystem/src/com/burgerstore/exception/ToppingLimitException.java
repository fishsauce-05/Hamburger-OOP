package com.burgerstore.exception;

public class ToppingLimitException extends Exception {
    public ToppingLimitException(String message) {
        super(message);
    }
}
package com.example.exceptions;

// Thrown when an order has missing information or an invalid price.
public class InvalidOrderException extends Exception {
    public InvalidOrderException(String message) {
        super(message);
    }
}

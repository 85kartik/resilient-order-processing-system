package com.example.exceptions;

// Thrown when an error happens while processing a valid order.
public class ProcessingException extends Exception {
    public ProcessingException(String message) {
        super(message);
    }
}

package com.example.util;

import com.example.orders.Order;
import com.example.exceptions.InvalidOrderException;

// Checks order details before the order is sent for processing.
public class OrderValidator {
    public static void validate(Order order) throws InvalidOrderException {
        if (order == null) {
            throw new InvalidOrderException("Order is missing.");
        }
        if (isBlank(order.getId()) || isBlank(order.getCustomerName()) || isBlank(order.getProductName())) {
            throw new InvalidOrderException("Order ID, customer name, and product name are required.");
        }
        if (order.getPrice() <= 0 || Double.isNaN(order.getPrice()) || Double.isInfinite(order.getPrice())) {
            throw new InvalidOrderException("Price must be a valid number greater than zero.");
        }
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}

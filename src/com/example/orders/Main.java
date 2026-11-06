package com.example.orders;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.example.exceptions.InvalidOrderException;
import com.example.util.OrderValidator;

public class Main {
    public static void main(String[] args) {
        // Five or more sample orders, including invalid cases and one processing failure.
        Order[] orders = {
            new Order("O101", "Asha", "Notebook", 12.50),
            new Order("O102", "Ravi", "Pen Set", 5.00),
            new Order("O103", "Mina", "Backpack", 35.00),
            new Order("O104", "Dev", "Failing Product", 20.00),
            new Order("O105", "", "Water Bottle", 8.00),
            new Order("O106", "Sara", "Desk Lamp", -4.00)
        };

        ExecutorService pool = Executors.newFixedThreadPool(3);
        try {
            for (Order order : orders) {
                try {
                    OrderValidator.validate(order);
                    pool.execute(new OrderProcessor(order));
                } catch (InvalidOrderException e) {
                    System.out.println("Order " + order.getId() + " rejected: " + e.getMessage());
                }
            }
        } finally {
            // Stop accepting work, then wait for submitted orders to finish.
            pool.shutdown();
            try {
                if (!pool.awaitTermination(1, TimeUnit.MINUTES)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
                System.out.println("Waiting for orders was interrupted.");
            }
        }
        System.out.println("All order tasks have been handled.");
    }
}

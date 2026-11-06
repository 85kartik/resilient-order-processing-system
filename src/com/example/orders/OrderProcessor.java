package com.example.orders;

import com.example.exceptions.ProcessingException;

// Runnable lets each order run as a separate task in the thread pool.
public class OrderProcessor implements Runnable {
    private Order order;

    public OrderProcessor(Order order) {
        this.order = order;
    }

    @Override
    public void run() {
        try {
            processOrder();
        } catch (ProcessingException e) {
            System.out.println("Order " + order.getId() + " failed: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Order " + order.getId() + " failed unexpectedly: " + e.getMessage());
        } finally {
            System.out.println("Finished attempt for order " + order.getId());
        }
    }

    private void processOrder() throws ProcessingException {
        // A named sample product simulates a processing problem for demonstration.
        if ("Failing Product".equals(order.getProductName())) {
            throw new ProcessingException("simulated processing error.");
        }
        System.out.println("Processing order " + order.getId() + " for " + order.getCustomerName()
                + ": " + order.getProductName() + " ($" + order.getPrice() + ")");
        System.out.println("Order " + order.getId() + " completed successfully.");
    }
}

package com.example.orders;

// Holds the information for one customer order.
public class Order {
    private String id;
    private String customerName;
    private String productName;
    private double price;

    public Order(String id, String customerName, String productName, double price) {
        this.id = id;
        this.customerName = customerName;
        this.productName = productName;
        this.price = price;
    }

    public String getId() { return id; }
    public String getCustomerName() { return customerName; }
    public String getProductName() { return productName; }
    public double getPrice() { return price; }
}

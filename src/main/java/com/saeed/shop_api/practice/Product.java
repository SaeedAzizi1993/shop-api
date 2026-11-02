package com.saeed.shop_api.practice;

public class Product {
    private Long id;
    private String name;
    private double price;
    private int stock;

    public Product(Long id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public void decreaseStock(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive, but was: " + quantity);
        }
        if (this.stock < quantity) {
            throw new OutOfStockException(name, quantity, stock);
        }
        stock -= quantity;
    }

}


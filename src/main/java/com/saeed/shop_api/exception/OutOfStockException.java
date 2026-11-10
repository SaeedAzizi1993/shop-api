package com.saeed.shop_api.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String name, int quantity, int stock) {
        super(name + ": requested " + quantity + ", but only " + stock + " in stock");
    }
}

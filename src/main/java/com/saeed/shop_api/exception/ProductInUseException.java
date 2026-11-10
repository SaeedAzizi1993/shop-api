package com.saeed.shop_api.exception;

public class ProductInUseException extends RuntimeException {
    public ProductInUseException(Long id) {
        super("product " + id + " cannot be deleted because it has orders");
    }
}

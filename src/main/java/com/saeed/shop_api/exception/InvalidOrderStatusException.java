package com.saeed.shop_api.exception;

import com.saeed.shop_api.entity.OrderStatus;

public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(OrderStatus from, OrderStatus to) {
        super("Cannot change order status from " + from + " to " + to);
    }
}

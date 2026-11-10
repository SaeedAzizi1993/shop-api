package com.saeed.shop_api.dto;

import com.saeed.shop_api.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;

public record OrderStatusRequest(
        @NotNull(message = "Status is required")
        OrderStatus status
) {
}

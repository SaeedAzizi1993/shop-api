package com.saeed.shop_api.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ApiError(
        int status,
        String message,
        Map<String, String> errors,
        LocalDateTime timestamp
) {
}
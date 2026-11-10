package com.saeed.shop_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // نگهبان ۱: محصول پیدا نشد → 404
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleNotFound(ProductNotFoundException ex) {
        return new ApiError(404, ex.getMessage(), null, LocalDateTime.now());
    }

    // نگهبان ۲: ورودی نامعتبر (@Valid رد شد) → 400
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiError handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return new ApiError(400, "Validation failed", errors, LocalDateTime.now());
    }

    //نگهبان3: موجودی کافی نیست <- 409
    @ExceptionHandler(OutOfStockException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleOutOfStock(OutOfStockException ex) {
        return new ApiError(409, ex.getMessage(), null, LocalDateTime.now());
    }

    //نگهبان 4: سفارش پیدا نشد <- 404
    @ExceptionHandler(OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiError handleOrderNotFound(OrderNotFoundException ex) {
        return new ApiError(404, ex.getMessage(), null, LocalDateTime.now());
    }

    @ExceptionHandler(ProductInUseException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ApiError handleProductInUse(ProductInUseException ex) {
        return new ApiError(409, ex.getMessage(), null, LocalDateTime.now());
    }
}
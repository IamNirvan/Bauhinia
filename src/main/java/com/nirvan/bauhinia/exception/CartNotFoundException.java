package com.nirvan.bauhinia.exception;

public class CartNotFoundException extends ApiRequestException {
    public CartNotFoundException(String message) {
        super(message);
    }
}

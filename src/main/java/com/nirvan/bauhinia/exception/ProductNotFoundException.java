package com.nirvan.bauhinia.exception;

public class ProductNotFoundException extends ApiRequestException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}

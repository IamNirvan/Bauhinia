package com.nirvan.bauhinia.exception;

public class ProductOrderNotFoundException extends ApiRequestException {
    public ProductOrderNotFoundException(String message) {
        super(message);
    }
}

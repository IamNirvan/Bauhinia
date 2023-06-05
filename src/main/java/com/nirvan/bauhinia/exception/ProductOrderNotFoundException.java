package com.nirvan.bauhinia.exception;

public class ProductOrderNotFoundException extends RuntimeException {
    public ProductOrderNotFoundException(String message) {
        super(message);
    }
}

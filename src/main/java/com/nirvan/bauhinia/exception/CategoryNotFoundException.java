package com.nirvan.bauhinia.exception;

public class CategoryNotFoundException extends ApiRequestException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}

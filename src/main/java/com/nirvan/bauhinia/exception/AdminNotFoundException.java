package com.nirvan.bauhinia.exception;

public class AdminNotFoundException extends ApiRequestException {
    public AdminNotFoundException(String message) {
        super(message);
    }
}

package com.nirvan.bauhinia.exception;

public class ItemNotFoundException extends ApiRequestException {
    public ItemNotFoundException(String message) {
        super(message);
    }
}

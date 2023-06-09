package com.nirvan.bauhinia.exception;

public class ReviewNotFoundException extends ApiRequestException {
    public ReviewNotFoundException(String message) {
        super(message);
    }
}

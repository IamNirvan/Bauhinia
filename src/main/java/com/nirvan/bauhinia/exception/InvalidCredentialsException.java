package com.nirvan.bauhinia.exception;

public class InvalidCredentialsException extends ApiRequestException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}

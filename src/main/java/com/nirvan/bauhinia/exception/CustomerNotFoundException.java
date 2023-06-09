package com.nirvan.bauhinia.exception;

public class CustomerNotFoundException extends ApiRequestException {

    public CustomerNotFoundException(String message) {
        super(message);
    }

}

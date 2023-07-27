package com.nirvan.bauhinia.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCustomerCredentialsRequest {
    private int id;
    private String email;
    private String password;
}

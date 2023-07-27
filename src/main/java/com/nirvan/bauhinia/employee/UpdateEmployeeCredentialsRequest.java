package com.nirvan.bauhinia.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateEmployeeCredentialsRequest {
    private int id;
    private String email;
    private String password;
}

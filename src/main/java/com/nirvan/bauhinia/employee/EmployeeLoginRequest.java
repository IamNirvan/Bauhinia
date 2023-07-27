package com.nirvan.bauhinia.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeLoginRequest {
    private String email;
    private String password;
}

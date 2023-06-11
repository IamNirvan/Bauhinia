package com.nirvan.bauhinia.employee.administrator;

import com.nirvan.bauhinia.employee.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity(name = "Administrator")
@DiscriminatorValue(value = "ADMINISTRATOR")
public class Administrator extends Employee {
    public Administrator(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

}



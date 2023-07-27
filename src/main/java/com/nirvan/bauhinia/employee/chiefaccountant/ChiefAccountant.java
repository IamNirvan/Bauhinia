package com.nirvan.bauhinia.employee.chiefaccountant;


import com.nirvan.bauhinia.employee.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity(name = "ChiefAccountant")
@DiscriminatorValue(value = "CHIEF_ACCOUNTANT")
public class ChiefAccountant extends Employee {

    public ChiefAccountant(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

}

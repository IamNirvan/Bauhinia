package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity
@DiscriminatorValue(value = "PRODUCTION_MANAGER")
public class ProductionManager extends Employee {

    public ProductionManager(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }
}

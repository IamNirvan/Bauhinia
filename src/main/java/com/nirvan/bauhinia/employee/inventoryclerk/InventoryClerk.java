package com.nirvan.bauhinia.employee.inventoryclerk;

import com.nirvan.bauhinia.employee.Employee;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
@Entity(name = "InventoryClerk")
@DiscriminatorValue(value = "INVENTORY_CLERK")
public class InventoryClerk extends Employee {

    public InventoryClerk(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password);
    }

}

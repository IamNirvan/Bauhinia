package com.nirvan.bauhinia.employee.inventoryclerk;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryClerkRepository extends EmployeeRepository<InventoryClerk> {

    boolean existsInventoryClerkByEmail(String email);

}

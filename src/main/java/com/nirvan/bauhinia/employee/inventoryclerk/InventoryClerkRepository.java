package com.nirvan.bauhinia.employee.inventoryclerk;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryClerkRepository extends EmployeeRepository<InventoryClerk> {

    boolean existsInventoryClerkByEmail(String email);

    Optional<InventoryClerk> findInventoryClerkByEmail(String email);

    @Query("SELECT count(e) from Employee e WHERE e.accountType = com.nirvan.bauhinia.employee.AccountType.INVENTORY_CLERK")
    Optional<Integer> getEmployeeCount();
}

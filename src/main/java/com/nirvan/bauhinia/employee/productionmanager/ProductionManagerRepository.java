package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionManagerRepository extends EmployeeRepository<ProductionManager> {

    boolean existsProductionManagerByEmail(String email);

    Optional<ProductionManager> findProductionManagerByEmail(String email);

    @Query("SELECT count(e) from Employee e WHERE e.accountType = com.nirvan.bauhinia.employee.AccountType.PRODUCTION_MANAGER")
    Optional<Integer> getEmployeeCount();
}

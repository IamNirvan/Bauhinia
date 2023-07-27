package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductionManagerRepository extends EmployeeRepository<ProductionManager> {

    boolean existsProductionManagerByEmail(String email);

    Optional<ProductionManager> findProductionManagerByEmail(String email);

}

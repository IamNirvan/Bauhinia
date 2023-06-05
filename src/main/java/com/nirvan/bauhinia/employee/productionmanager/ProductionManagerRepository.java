package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionManagerRepository extends EmployeeRepository<ProductionManager> {

    boolean existsProductionManagerByEmail(String email);

}

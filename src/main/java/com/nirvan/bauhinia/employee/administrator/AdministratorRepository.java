package com.nirvan.bauhinia.employee.administrator;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdministratorRepository extends EmployeeRepository<Administrator> {

    boolean existsAdministratorByEmail(String email);
}

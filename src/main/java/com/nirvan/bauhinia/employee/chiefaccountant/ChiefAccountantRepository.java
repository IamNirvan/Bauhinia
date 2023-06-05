package com.nirvan.bauhinia.employee.chiefaccountant;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiefAccountantRepository extends EmployeeRepository<ChiefAccountant> {

    boolean existsChiefAccountantByEmail(String email);

}

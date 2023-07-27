package com.nirvan.bauhinia.employee.chiefaccountant;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChiefAccountantRepository extends EmployeeRepository<ChiefAccountant> {

    boolean existsChiefAccountantByEmail(String email);

    Optional<ChiefAccountant> findChiefAccountantByEmail(String email);

    @Query("SELECT count(e) from Employee e WHERE e.accountType = com.nirvan.bauhinia.employee.AccountType.CHIEF_ACCOUNTANT")
    Optional<Integer> getEmployeeCount();
}

package com.nirvan.bauhinia.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends JpaRepository<T, Integer> {

    @Query("SELECT e from Employee e WHERE e.email=?1")
    boolean existsByEmail(String email);

}

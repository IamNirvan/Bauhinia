package com.nirvan.bauhinia.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EmployeeRepository<T extends Employee> extends JpaRepository<T, Integer> {

}

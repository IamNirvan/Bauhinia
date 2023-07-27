package com.nirvan.bauhinia.customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    boolean existsCustomerByEmail(String email);

    Optional<Customer> findCustomerByContactNumber1(String number);
    Optional<Customer> findCustomerByContactNumber2(String number);

    @Query("SELECT c FROM Customer c WHERE c.contactNumber1 = ?1 OR c.contactNumber2 = ?1")
    Optional<Customer> findCustomerByContactNumber(String number);

    @Query("SELECT a FROM Address  a WHERE a.id = ?1")
    Optional<Customer> findCustomerByAddressId(int Id);

    Optional<Customer> findCustomerByEmail(String email);

    @Query("SELECT count(c) FROM Customer c")
    Optional<Integer> getRegisteredCustomerCount();
}

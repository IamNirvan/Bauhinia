package com.nirvan.bauhinia.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/customers")
public class CustomerController {

    private final CustomerService SERVICE;

    @Autowired
    public CustomerController(CustomerService service) {
        SERVICE = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> fetchAllCustomers() {
        List<Customer> customers  = SERVICE.select();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping( "/find")
    public ResponseEntity<Customer> findCustomerById(@RequestParam(name = "customerId") int id) {
        return new ResponseEntity<>(SERVICE.findCustomerById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer) {
        Customer addedCustomer = SERVICE.insert(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PutMapping
    public Customer updateCustomer(@RequestBody Customer updatedCustomer) {
        return SERVICE.update(updatedCustomer);
    }

    @PutMapping("/excludingAddress")
    public Customer updateCustomerExcludingAddress(@RequestBody Customer updatedCustomer) {
        return SERVICE.updateExcludingAddress(updatedCustomer);
    }

    @DeleteMapping(path = "/{customerId}")
    public boolean deleteCustomer(@PathVariable("customerId") int customerId) {
        return SERVICE.delete(customerId);
    }

}

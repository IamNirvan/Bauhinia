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

    @PostMapping("/insert")
    public ResponseEntity<Customer> signUp(@RequestBody Customer customer) {
        Customer addedCustomer = SERVICE.insert(customer);
        return new ResponseEntity<>(addedCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer updatedCustomer) {
        Customer customer = SERVICE.update(updatedCustomer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @PutMapping("/update/excludingAddress")
    public ResponseEntity<Customer> updateCustomerExcludingAddress(@RequestBody Customer updatedCustomer) {
        Customer customer = SERVICE.updateExcludingAddress(updatedCustomer);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable("customerId") int customerId) {
        SERVICE.delete(customerId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

package com.nirvan.bauhinia.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/customers")
@RequiredArgsConstructor
public class CustomerControllerV2 {
    private final CustomerServiceV2 customerService;

    /**
     * Provides information regarding all the customers in the system
     * */
    @GetMapping
    public ResponseEntity<List<Customer>> fetchAllCustomers() {
        return new ResponseEntity<>(customerService.fetchAllCustomers(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific customer in the system
     * @param customerId the id of the customer
     * */
    @GetMapping("/find")
    public ResponseEntity<Customer> fetchCustomerById(@RequestParam("id") int customerId) {
        return new ResponseEntity<>(customerService.fetchCustomerById(customerId), HttpStatus.OK);
    }

    /**
     * Adds a customer into the system
     * @param customer a customer object
     * */
    @PostMapping
    public ResponseEntity<String> addCustomer(@RequestBody Customer customer) {
        boolean registered = customerService.addCustomer(customer);
        if(registered) {
            return new ResponseEntity<>("Token must be returned...", HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Updates the non-sensitive information of an existing customer object
     * @param customerId the id of the targeted customer
     * @param firstName the new first name. Optional
     * @param lastName the new last name. Optional
     * @param contactNumber1 the new, first contact number. Optional
     * @param contactNumber2 the new, second contact number. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateCustomer(
            @RequestParam(name = "customerId") int customerId,
            @RequestParam(name = "fName", required = false) String firstName,
            @RequestParam(name = "lName", required = false) String lastName,
            @RequestParam(name = "contact1", required = false) String contactNumber1,
            @RequestParam(name = "contact2", required = false) String contactNumber2
    ) {
        return new ResponseEntity<>(
                customerService.updateCustomer(customerId,firstName,lastName,contactNumber1,contactNumber2),
                HttpStatus.OK
        );
    }

    /**
     * Updates the credentials (email and password) of a customer object
     * @param request an object of UpdateCustomerCredentialsRequest
     * */
    @PutMapping("/credentials")
    public ResponseEntity<Boolean> updateCustomer(@RequestBody UpdateCustomerCredentialsRequest request) {
        return new ResponseEntity<>(customerService.updateCredentials(request), HttpStatus.OK);
    }

    /**
     * Deletes an existing customer object
     * @param customerId the id of the targeted customer
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteCustomer(@RequestParam("id") int customerId) {
        return new ResponseEntity<>(customerService.deleteCustomer(customerId), HttpStatus.OK);
    }
}

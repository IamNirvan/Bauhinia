package com.nirvan.bauhinia.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/customers")
@RequiredArgsConstructor
@CrossOrigin
public class CustomerControllerV2 {
    private final CustomerServiceV2 CUSTOMER_SERVICE;

    @GetMapping("/stats")
    public ResponseEntity<CustomerStats> getRegisteredCustomerCount() {
        return ResponseEntity.ok(CUSTOMER_SERVICE.getRegisteredCustomerCount());
    }

    /**
     * Provides information regarding all the customers in the system
     * */
    @GetMapping
    public ResponseEntity<List<Customer>> fetchAllCustomers() {
        return new ResponseEntity<>(CUSTOMER_SERVICE.fetchAllCustomers(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific customer in the system
     * @param customerId the id of the customer
     * */
    @GetMapping("/{id}")
    public ResponseEntity<Customer> fetchCustomerById(@PathVariable("id") int customerId) {
        return new ResponseEntity<>(CUSTOMER_SERVICE.fetchCustomerById(customerId), HttpStatus.OK);
    }

    @GetMapping("/find/contactNumber/{number}")
    public ResponseEntity<Customer> fetchCustomerByContactNumber(@PathVariable("number") String number) {
        return ResponseEntity.ok(CUSTOMER_SERVICE.fetchCustomerByContactNumber(number));
    }

    @GetMapping("/find/email/{email}")
    public ResponseEntity<Customer> fetchCustomerByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(CUSTOMER_SERVICE.fetchCustomerByEmail(email));
    }

    /**
     * Allows a customer to be logged into the system
     * @param loginRequest a request object of type CustomerLoginRequest
     * */
    @PostMapping("/login")
    public ResponseEntity<Customer> login(@RequestBody CustomerLoginRequest loginRequest) {
        return ResponseEntity.ok(CUSTOMER_SERVICE.login(loginRequest));
    }

    /**
     * Adds a customer into the system
     * @param customer a customer object
     * */
    @PostMapping
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(CUSTOMER_SERVICE.addCustomer(customer));
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
                CUSTOMER_SERVICE.updateCustomer(customerId,firstName,lastName,contactNumber1,contactNumber2),
                HttpStatus.OK
        );
    }

    /**
     * Updates the credentials (email and password) of a customer object
     * @param request an object of UpdateCustomerCredentialsRequest
     * */
    @PutMapping("/credentials")
    public ResponseEntity<Boolean> updateCustomer(@RequestBody UpdateCustomerCredentialsRequest request) {
        return new ResponseEntity<>(CUSTOMER_SERVICE.updateCredentials(request), HttpStatus.OK);
    }

    /**
     * Deletes an existing customer object
     * @param customerId the id of the targeted customer
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteCustomer(@RequestParam("id") int customerId) {
        return new ResponseEntity<>(CUSTOMER_SERVICE.deleteCustomer(customerId), HttpStatus.OK);
    }
}

package com.nirvan.bauhinia.employee.inventoryclerk;

import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.employee.UpdateEmployeeCredentialsRequest;
import com.nirvan.bauhinia.employee.chiefaccountant.ChiefAccountantStats;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/inventory-clerks")
@RequiredArgsConstructor
@CrossOrigin
public class InventoryClerkControllerV2 {
    private final InventoryClerkServiceV2 IC_SERVICE;

    @GetMapping("/stats")
    public ResponseEntity<InventoryClerkStats> getInventoryClerkCount() {
        return ResponseEntity.ok(IC_SERVICE.getInventoryClerkCount());
    }

    /**
     * Provides all the inventory clerks
     * */
    @GetMapping
    public ResponseEntity<List<InventoryClerk>> fetchAllInventoryClerks() {
        return new ResponseEntity<>(IC_SERVICE.fetchAllInventoryClerks(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific inventory clerk
     * @param employeeId id of the targeted inventory clerk
     * */
    @GetMapping("/find")
    public ResponseEntity<InventoryClerk> fetchInventoryClerkById(@RequestParam("id") int employeeId) {
        return new ResponseEntity<>(IC_SERVICE.fetchInventoryClerkById(employeeId), HttpStatus.OK);
    }

    /**
     * Allows an inventory clerk to log into the system
     * @param loginRequest an instance of EmployeeLoginRequest
     * */
    @PostMapping("/login")
    public ResponseEntity<InventoryClerk> login(@RequestBody EmployeeLoginRequest loginRequest) {
        return new ResponseEntity<>(IC_SERVICE.login(loginRequest), HttpStatus.OK);
    }

    /**
     * Adds an inventory clerk into the system
     * @param inventoryClerk an object of InventoryClerk
     * */
    @PostMapping
    public ResponseEntity<Boolean> addInventoryClerk(@RequestBody InventoryClerk inventoryClerk) {
        return new ResponseEntity<>(IC_SERVICE.addInventoryClerk(inventoryClerk), HttpStatus.CREATED);
    }

    /**
     * Updates the non-sensitive information about an inventory clerk
     * @param employeeId id of the targeted inventory clerk
     * @param firstName the new first name. Optional
     * @param lastName the new last name. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateInventoryClerk(
            @RequestParam("id") int employeeId,
            @RequestParam(name = "fName", required = false) String firstName,
            @RequestParam(name = "lName", required = false) String lastName
    ) {
        return new ResponseEntity<>(IC_SERVICE.updateInventoryClerk(employeeId, firstName, lastName), HttpStatus.OK);
    }

    /**
     * Updates the credentials of an inventory clerk
     * @param credentialsRequest an instance of UpdateEmployeeCredentialsRequest
     * */
    @PutMapping("/credentials")
    public ResponseEntity<Boolean> updateCredentials(@RequestBody UpdateEmployeeCredentialsRequest credentialsRequest) {
        return new ResponseEntity<>(IC_SERVICE.updateCredentials(credentialsRequest), HttpStatus.OK);
    }

    /**
     * Deletes an existing inventory clerk
     * @param employeeId id of the targeted inventory clerk
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventoryClerk(@PathVariable("id") int employeeId) {
        return new ResponseEntity<>(IC_SERVICE.deleteInventoryClerk(employeeId), HttpStatus.OK);
    }
}

package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.employee.UpdateEmployeeCredentialsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/production-managers")
@RequiredArgsConstructor
public class ProductionManagerController {
    private final ProductionManagerService PM_SERVICE;

    /**
     * Provides all the production managers
     * */
    @GetMapping
    public ResponseEntity<List<ProductionManager>> fetchAllProductionManagers() {
        return new ResponseEntity<>(PM_SERVICE.fetchAllProductionManagers(), HttpStatus.OK);
    }

    /**
     * Provides the information regarding a specific production manager
     * @param employeeId id of the targeted production manager
     * */
    @GetMapping("/find")
    public ResponseEntity<ProductionManager> fetchProductionManagerById(@RequestParam("id") int employeeId) {
        return new ResponseEntity<>(PM_SERVICE.fetchProductionManagerById(employeeId), HttpStatus.OK);
    }

    /**
     * Allows a production manager to log into the system
     * @param loginRequest an instance of EmployeeLoginRequest
     * */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody EmployeeLoginRequest loginRequest) {
        return new ResponseEntity<>(PM_SERVICE.login(loginRequest), HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<Boolean> addProductionManager(@RequestBody ProductionManager productionManager) {
        return new ResponseEntity<>(PM_SERVICE.addProductionManager(productionManager), HttpStatus.CREATED);
    }

    /**
     * Updates the non-sensitive information about a production manager
     * @param employeeId id of the targeted production manager
     * @param firstName the new first name. Optional
     * @param lastName the new last name. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateProductionManager(
            @RequestParam("id") int employeeId,
            @RequestParam(name = "fName", required = false) String firstName,
            @RequestParam(name = "lName", required = false) String lastName
    ) {
        return new ResponseEntity<>(PM_SERVICE.updateProductionManager(employeeId, firstName, lastName), HttpStatus.OK);
    }

    /**
     * Updates the credentials of the production manager
     * @param credentialsRequest an instance of UpdateEmployeeCredentialsRequest
     * */
    @PutMapping("/credentials")
    public ResponseEntity<Boolean> updateCredentials(@RequestBody UpdateEmployeeCredentialsRequest credentialsRequest) {
        return new ResponseEntity<>(PM_SERVICE.updateCredentials(credentialsRequest), HttpStatus.OK);
    }

    /**
     * Deletes an existing production manager
     * @param employeeId id of the targeted production manager
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteProductionManager(@RequestParam("id") int employeeId) {
        return new ResponseEntity<>(PM_SERVICE.deleteProductionManager(employeeId), HttpStatus.OK);
    }
}

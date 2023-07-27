package com.nirvan.bauhinia.employee.administrator;

import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.employee.UpdateEmployeeCredentialsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/administrators")
@RequiredArgsConstructor
@CrossOrigin
public class AdministratorControllerV2 {
    private final AdministratorServiceV2 ADMIN_SERVICE;

    /**
     * Provides information regarding a specific admin
     * @param employeeId id of the targeted administrator
     * */
    @GetMapping("/find")
    public ResponseEntity<Administrator> fetchById(@RequestParam("id") int employeeId) {
        return new ResponseEntity<>(ADMIN_SERVICE.fetchAdminById(employeeId), HttpStatus.OK);
    }

    /**
     * Allows an administrator to be logged in to the system
     * @param loginRequest a request object of type EmployeeLoginRequest
     * */
    @PostMapping("/login")
    public ResponseEntity<Administrator> login(@RequestBody EmployeeLoginRequest loginRequest) {
        return new ResponseEntity<>(ADMIN_SERVICE.login(loginRequest), HttpStatus.OK);
    }

    /**
     * Allows an administrator to be added
     * @param administrator administrator object
     * */
    @PostMapping
    public ResponseEntity<Boolean> addAdministrator(@RequestBody Administrator administrator) {
        return new ResponseEntity<>(ADMIN_SERVICE.addAdministrator(administrator), HttpStatus.CREATED);
    }

    /**
     * Updates the non-sensitive information of an administrator
     * @param employeeId the id of the targeted administrator
     * @param firstName the new first name. Optional
     * @param lastName the new last name. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateAdministrator(
            @RequestParam("id") int employeeId,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName
            ) {
        return new ResponseEntity<>(ADMIN_SERVICE.updateBasicDetails(employeeId, firstName, lastName), HttpStatus.OK);
    }

    /**
     * Updates the credentials of a specific administrator
     * @param updateCredentialsRequest a request of type UpdateCredentialsRequest
     * */
    @PutMapping("/credentials")
    public ResponseEntity<Boolean> updateCredentials(@RequestBody UpdateEmployeeCredentialsRequest updateCredentialsRequest) {
        return new ResponseEntity<>(ADMIN_SERVICE.updateCredentials(updateCredentialsRequest), HttpStatus.OK);
    }

    /**
     * Deletes an existing administrator
     * @param employeeId id of the targeted administrator
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAdministrator(@PathVariable("id") int employeeId) {
        return new ResponseEntity<>(ADMIN_SERVICE.deleteAdministrator(employeeId), HttpStatus.OK);
    }
}

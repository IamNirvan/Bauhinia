package com.nirvan.bauhinia.employee.chiefaccountant;

import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v2/accountants")
@RequiredArgsConstructor
public class ChiefAccountantController {
    private final ChiefAccountantService CA_SERVICE;

    /**
     * Provides all the chief accountants
     * */
    @GetMapping
    public ResponseEntity<List<ChiefAccountant>> fetchAll() {
        return new ResponseEntity<>(CA_SERVICE.fetchAllChiefAccountants(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific chief accountant
     * @param employeeId id of the targeted chief accountant
     * */
    @GetMapping("/find")
    public ResponseEntity<ChiefAccountant> fetchById(@RequestParam(name = "id") int employeeId) {
        return new ResponseEntity<>(CA_SERVICE.fetchChiefAccountantById(employeeId), HttpStatus.OK);
    }

    /**
     * Allows a chief accountant to log into the system
     * @param loginRequest an instance of EmployeeLoginRequest
     * */
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody EmployeeLoginRequest loginRequest) {
        return new ResponseEntity<>(CA_SERVICE.login(loginRequest), HttpStatus.OK);
    }

    /**
     * Adds a new chief accountant
     * @param chiefAccountant an instance of ChiefAccountant
     * */
    @PostMapping
    public ResponseEntity<ChiefAccountant> addChiefAccountant(@RequestBody ChiefAccountant chiefAccountant) {
        return new ResponseEntity<>(CA_SERVICE.addChiefAccountant(chiefAccountant), HttpStatus.CREATED);
    }

    /**
     * Updates the non-sensitive information about a chief accountant
     * @param employeeId id of the targeted chief accountant
     * @param firstName the new first name. Optional
     * @param lastName the new last name. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateChiefAccountant(
            @RequestParam("id") int employeeId,
            @RequestParam(name = "fName", required = false) String firstName,
            @RequestParam(name = "lName", required = false) String lastName
    ) {
        return new ResponseEntity<>(CA_SERVICE.update(employeeId, firstName, lastName), HttpStatus.OK);
    }

    /**
     * Deletes an existing chief accountant
     * @param employeeId id of the targeted chief accountant
     * */
    @DeleteMapping
    public ResponseEntity<?> deleteChiefAccountant(@RequestParam("id") int employeeId) {
        return new ResponseEntity<>(CA_SERVICE.deleteChiefAccountant(employeeId), HttpStatus.OK);
    }

}

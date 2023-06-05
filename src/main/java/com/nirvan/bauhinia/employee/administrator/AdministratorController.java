package com.nirvan.bauhinia.employee.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/admins")
public class AdministratorController {

    private final AdministratorService ADMIN_SERVICE;

    @Autowired
    public AdministratorController(AdministratorService adminService) {
        ADMIN_SERVICE = adminService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Administrator>> fetchAll() {
        return new ResponseEntity<>(ADMIN_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Administrator> fetchById(@RequestParam(name = "employeeId") int id) {
        return new ResponseEntity<>(ADMIN_SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Administrator> addAdministrator(@RequestBody Administrator administrator) {
        return new ResponseEntity<>(ADMIN_SERVICE.insert(administrator), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Administrator> updateAdministrator(@RequestBody Administrator administrator) {
        return new ResponseEntity<>(ADMIN_SERVICE.update(administrator), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteAdministrator(@PathVariable("employeeId") int employeeId) {
        ADMIN_SERVICE.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

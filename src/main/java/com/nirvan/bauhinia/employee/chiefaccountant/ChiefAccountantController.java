package com.nirvan.bauhinia.employee.chiefaccountant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/chiefAccountants")
public class ChiefAccountantController {

    private final ChiefAccountantService CA_SERVICE;

    @Autowired
    public ChiefAccountantController(ChiefAccountantService chiefAccountantService) {
        CA_SERVICE = chiefAccountantService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChiefAccountant>> fetchAll() {
        return new ResponseEntity<>(CA_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ChiefAccountant> fetchById(@RequestParam(name = "employeeId") int id) {
        return new ResponseEntity<>(CA_SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ChiefAccountant> addChiefAccountant(@RequestBody ChiefAccountant chiefAccountant) {
        return new ResponseEntity<>(CA_SERVICE.insert(chiefAccountant), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ChiefAccountant> updateChiefAccountant(@RequestBody ChiefAccountant chiefAccountant) {
        return new ResponseEntity<>(CA_SERVICE.update(chiefAccountant), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteChiefAccountant(@PathVariable("employeeId") int employeeId) {
        CA_SERVICE.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

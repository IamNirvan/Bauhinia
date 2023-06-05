package com.nirvan.bauhinia.employee.inventoryclerk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/inventoryClerks")
public class InventoryClerkController {

    private final InventoryClerkService IC_SERVICE;

    @Autowired
    public InventoryClerkController(InventoryClerkService inventoryClerkService) {
        IC_SERVICE = inventoryClerkService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<InventoryClerk>> fetchAll() {
        return new ResponseEntity<>(IC_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<InventoryClerk> fetchById(@RequestParam(name = "employeeId") int id) {
        return new ResponseEntity<>(IC_SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<InventoryClerk> addInventoryClerk(@RequestBody InventoryClerk inventoryClerk) {
        return new ResponseEntity<>(IC_SERVICE.insert(inventoryClerk), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<InventoryClerk> updateInventoryClerk(@RequestBody InventoryClerk inventoryClerk) {
        return new ResponseEntity<>(IC_SERVICE.update(inventoryClerk), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteInventoryClerk(@PathVariable("employeeId") int employeeId) {
        IC_SERVICE.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

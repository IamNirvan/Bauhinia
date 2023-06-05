package com.nirvan.bauhinia.employee.productionmanager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/productionManagers")
public class ProductionManagerController {

    private final ProductionManagerService PM_SERVICE;

    @Autowired
    public ProductionManagerController(ProductionManagerService productionManagerService) {
        PM_SERVICE = productionManagerService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductionManager>> fetchAll() {
        return new ResponseEntity<>(PM_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ProductionManager> fetchById(@RequestParam(name = "employeeId") int id) {
        return new ResponseEntity<>(PM_SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ProductionManager> addProductionManager(@RequestBody ProductionManager productionManager) {
        return new ResponseEntity<>(PM_SERVICE.insert(productionManager), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<ProductionManager> updateProductionManager(@RequestBody ProductionManager productionManager) {
        return new ResponseEntity<>(PM_SERVICE.update(productionManager), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{employeeId}")
    public ResponseEntity<?> deleteProductionManager(@PathVariable("employeeId") int employeeId) {
        PM_SERVICE.delete(employeeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

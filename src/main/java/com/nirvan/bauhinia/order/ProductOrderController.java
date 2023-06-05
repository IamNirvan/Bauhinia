package com.nirvan.bauhinia.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/orders")
public class ProductOrderController {

    private final ProductOrderService PO_SERVICE;

    @Autowired
    public ProductOrderController(ProductOrderService poService) {
        PO_SERVICE = poService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductOrder>> fetchAll() {
        return new ResponseEntity<>(PO_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<ProductOrder> fetchById(@RequestParam(name = "orderId") int id) {
        return new ResponseEntity<>(PO_SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<ProductOrder> addProductOrder(@RequestBody ProductOrder order) {
        return new ResponseEntity<>(PO_SERVICE.insert(order), HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}/customer/{customerId}")
    public ResponseEntity<ProductOrder> assignCustomerToProductOrder(
            @PathVariable("customerId") int customerId,
            @PathVariable("orderId") int orderId
    ) {
        return new ResponseEntity<>(PO_SERVICE.assignCustomerToProductOrder(customerId, orderId), HttpStatus.OK);
    }

    @PutMapping("/{orderId}/item/{itemId}")
    public ResponseEntity<ProductOrder> assignItemToProductOrder(
            @PathVariable("itemId") int itemId,
            @PathVariable("orderId") int orderId
    ) {
        return new ResponseEntity<>(PO_SERVICE.assignItemToProductOrder(itemId, orderId), HttpStatus.OK);
    }

    @DeleteMapping(path = "/delete/{orderId}")
    public ResponseEntity<?> deleteProductOrder(@PathVariable("orderId") int orderId) {
        PO_SERVICE.delete(orderId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

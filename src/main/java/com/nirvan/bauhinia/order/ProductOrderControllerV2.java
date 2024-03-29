package com.nirvan.bauhinia.order;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v2/orders")
@RequiredArgsConstructor
@CrossOrigin
public class ProductOrderControllerV2 {
    private final ProductOrderServiceV2 PRODUCT_ORDER_SERVICE;

    @GetMapping("/stats")
    public ResponseEntity<ProductOrderStats> getProductOrderCount() {
        return ResponseEntity.ok(PRODUCT_ORDER_SERVICE.getProductOrderCount());
    }

    /**
     * Provides information regarding all orders
     * */
    @GetMapping
    public ResponseEntity<List<ProductOrder>> fetchAll() {
        return new ResponseEntity<>(PRODUCT_ORDER_SERVICE.fetchAllProductOrders(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific order
     * @param orderId id of the targeted order
     * */
    @GetMapping("/find")
    public ResponseEntity<ProductOrder> fetchById(@RequestParam("id") int orderId) {
        return new ResponseEntity<>(PRODUCT_ORDER_SERVICE.fetchProductOrderById(orderId), HttpStatus.OK);
    }

//    /**
//     * Adds a new product order along with the customer who owns it
//     * @param order order object
//     * @param customerId id of the targeted customer
//     * */
//    @Deprecated
//    @PostMapping("/customer/{cusId}/shipping/{addr1}/billing/{addr2}")
//    public ResponseEntity<ProductOrder> addProductOrder(
//            @RequestBody ProductOrder order,
//            @PathVariable("cusId")  int customerId,
//            @PathVariable("addr1")  int shippingAddrId,
//            @PathVariable("addr2")  int billingAddrId
//
//    ) {
//        return new ResponseEntity<>(PRODUCT_ORDER_SERVICE.addProductOrder(order, customerId, shippingAddrId, billingAddrId), HttpStatus.CREATED);
//    }

    /**
     * Adds a new product order along with the customer who owns it
     * @param order order object
     * @param customerId id of the targeted customer
     * */
    @PostMapping("/customer/{cusId}")
    public ResponseEntity<ProductOrder> addProductOrderV2(
            @RequestBody ProductOrder order,
            @PathVariable("cusId")  int customerId
    ) {
        return new ResponseEntity<>(PRODUCT_ORDER_SERVICE.addProductOrderV2(order, customerId), HttpStatus.CREATED);
    }

    /**
     * Updates the parameters of an existing order either individually or in unison
     * @param productOrderId id of the targeted order
     * @param price the new price. Optional
     * @param date the new date. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateProductOrder(
            @RequestParam("id") int productOrderId,
            @RequestParam(name = "price", required = false) Double price,
            @RequestParam(name = "date", required = false) LocalDate date
    ) {
        return new ResponseEntity<>(
                PRODUCT_ORDER_SERVICE.updateProductOrder(productOrderId, price, date),
                HttpStatus.OK
        );
    }

    /**
     * Deletes an existing order
     * @param orderId id of the targeted order
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteProductOrder(@RequestParam("id") int orderId) {
        return new ResponseEntity<>(PRODUCT_ORDER_SERVICE.delete(orderId), HttpStatus.OK);
    }
}

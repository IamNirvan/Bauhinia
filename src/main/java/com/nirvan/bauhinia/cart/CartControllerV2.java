package com.nirvan.bauhinia.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v2/carts")
@RequiredArgsConstructor
@CrossOrigin
public class CartControllerV2 {
    private final CartServiceV2 CART_SERVICE;

    /**
     * Provides information regarding all the items within a specific cart
     * */
    @GetMapping("/{cartId}")
    public ResponseEntity<Cart> fetchCartById(@PathVariable("cartId") int cartId) {
        return new ResponseEntity<>(CART_SERVICE.fetchCartById(cartId), HttpStatus.OK);
    }
}

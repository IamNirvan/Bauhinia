package com.nirvan.bauhinia.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v2/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService CART_SERVICE;

    /**
     * Provides information regarding all the items within a specific cart
     * */
    @GetMapping("/find")
    public ResponseEntity<Cart> fetchCartById(@RequestParam(name = "cartId") int cartId) {
        return new ResponseEntity<>(CART_SERVICE.fetchCartById(cartId), HttpStatus.OK);
    }
}

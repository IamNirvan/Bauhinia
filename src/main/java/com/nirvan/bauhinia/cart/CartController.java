package com.nirvan.bauhinia.cart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/carts")
public class CartController {

    private final CartService CART_SERVICE;

    @Autowired
    public CartController(CartService cartService) {
        CART_SERVICE = cartService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> fetchAllCarts() {
        List<Cart> carts = CART_SERVICE.select();
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Cart> fetchCartById(@RequestParam(name = "cartId") int id) {
        return new ResponseEntity<>(CART_SERVICE.selectById(id), HttpStatus.OK);
    }
}

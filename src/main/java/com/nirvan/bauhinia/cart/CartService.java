package com.nirvan.bauhinia.cart;

import com.nirvan.bauhinia.exception.CartNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository CART_REPOSITORY;


    @Autowired
    public CartService(CartRepository cartRepository) {
        CART_REPOSITORY = cartRepository;
    }

    public List<Cart> select() {
        return CART_REPOSITORY.findAll();
    }

    public Cart selectById(int id) {
        return CART_REPOSITORY.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart with id (" + id + ") does not exist"));
    }

}

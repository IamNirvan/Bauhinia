package com.nirvan.bauhinia.cart;

import com.nirvan.bauhinia.exception.CartNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository CART_REPOSITORY;
    private static final String ID_NOT_FOUND_MESSAGE = "Cart with the following id does not exist: %s";

    public Cart fetchCartById(int cartId) {
        return CART_REPOSITORY.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, cartId)));
    }

    public boolean saveCart(Cart cart) {
        CART_REPOSITORY.save(cart);
        return true;
    }
}

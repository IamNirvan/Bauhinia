package com.nirvan.bauhinia.cart;

import com.nirvan.bauhinia.exception.CartNotFoundException;
import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.item.ItemServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceV2 {
    private final CartRepository CART_REPOSITORY;
//    private final ItemServiceV2 ITEM_SERVICE;
    private static final String ID_NOT_FOUND_MESSAGE = "Cart with the following id does not exist: %s";

    public Cart fetchCartById(int cartId) {
        return CART_REPOSITORY.findById(cartId)
                .orElseThrow(() -> new CartNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, cartId)));
    }

    public boolean saveCart(Cart cart) {
        CART_REPOSITORY.save(cart);
        return true;
    }


//    public void removeItems(List<Integer> itemIds, int cartId) {
//        Cart cart = fetchCartById(cartId);
//        List<Item> itemList = cart.getItems();
//
//        itemIds.forEach(itemId -> {
//            Item item = ITEM_SERVICE.fetchItemById(itemId);
//            itemList.remove(item);
//        });
//        cart.setItems(itemList);
//    }
}

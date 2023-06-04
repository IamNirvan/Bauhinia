package com.nirvan.bauhinia.item;

import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.cart.CartRepository;
import com.nirvan.bauhinia.exception.CartNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ItemNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ItemService {

    private final ItemRepository ITEM_REPOSITORY;
    private final CartRepository CART_REPOSITORY;

    @Autowired
    public ItemService(ItemRepository itemRepository, CartRepository cartRepository) {
        ITEM_REPOSITORY = itemRepository;
        CART_REPOSITORY = cartRepository;
    }

    private Item getItem(int id) {
        return ITEM_REPOSITORY.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item with id (" + id + ") does not exist"));
    }

    public List<Item> select() {
        return ITEM_REPOSITORY.findAll();
    }

    public Item selectById(int id) {
        return getItem(id);
    }

    public Item selectBySku(String sku) {
        return ITEM_REPOSITORY.findBySku(sku)
                .orElseThrow(() -> new ItemNotFoundException("Item with sku (" + sku + ") does not exist"));
    }

    public Item insert(Item item) {
        // Make sure sku is unique and valid
        if(item.getSku().length() != 0) {
            if(ITEM_REPOSITORY.existsItemBySku(item.getSku())) {
                throw new InvalidParameterException("sku (" + item.getSku() + ") already exists");
            }
        }
        else {
            throw new InvalidParameterException("sku (" + item.getSku() + ") is invalid");
        }

        // Check if size is valid
        if(item.getSize().length() == 0) {
            throw new InvalidParameterException("Size (" + item.getSize() + ") is invalid");
        }

        // Check if colour is valid
        if(item.getColour().length() == 0) {
            throw new InvalidParameterException("Colour (" + item.getColour() + ") is invalid");
        }

        // Check if cost is valid
        if(item.getCost() < 0) {
            throw new InvalidParameterException("Cost (" + item.getCost() + ") is invalid");
        }

        ITEM_REPOSITORY.save(item);
        return item;
    }

    @Transactional
    public Item update(Item updatedItem) {
        Item persistedItem = getItem(updatedItem.getId());

        // Make sure sku is unique
        if(updatedItem.getSku().length() != 0) {
            if(!Objects.equals(persistedItem.getSku(), updatedItem.getSku())) {
                if(ITEM_REPOSITORY.existsItemBySku(updatedItem.getSku())) {
                    throw new InvalidParameterException("sku (" + updatedItem.getSku() + ") already exists");
                } else {
                    persistedItem.setSku(updatedItem.getSku());
                }
            }
        }
        else {
            throw new InvalidParameterException("sku (" + updatedItem.getSku() + ") is invalid");
        }

        // Check if size is valid
        if(updatedItem.getSize().length() != 0) {
            if(!Objects.equals(persistedItem.getSize(), updatedItem.getSize())) {
                persistedItem.setSize(updatedItem.getSize());
            }
        }
        else {
            throw new InvalidParameterException("Size (" + updatedItem.getSize() + ") is invalid");
        }

        // Check if colour is valid
        if(updatedItem.getColour().length() != 0) {
            if(!Objects.equals(persistedItem.getColour(), updatedItem.getColour())) {
                persistedItem.setColour(updatedItem.getColour());
            }
        }
        else {
            throw new InvalidParameterException("Colour (" + updatedItem.getColour() + ") is invalid");
        }

        // Check if colour is valid
        if(updatedItem.getCost() > 0) {
            if(!Objects.equals(persistedItem.getCost(), updatedItem.getCost())) {
                persistedItem.setCost(updatedItem.getCost());
            }
        }
        else {
            throw new InvalidParameterException("Cost (" + updatedItem.getCost() + ") is invalid");
        }

        return persistedItem;
    }

    public void delete(int itemId) {
        if(!ITEM_REPOSITORY.existsById(itemId)) {
            throw new ItemNotFoundException("Item with id (" + itemId + ") does not exist");
        }
        ITEM_REPOSITORY.deleteById(itemId);
    }

    @Transactional
    public Cart addItemToCart(int itemId, int cartId) {
        Item item = getItem(itemId);
        Cart cart = CART_REPOSITORY.findById(cartId)
                .orElseThrow(() ->  new CartNotFoundException("Cart with id (" + cartId + ") does not exist"));

        List<Item> cartItems = cart.getItems();
        cartItems.add(item);
        cart.setItems(cartItems);

        List<Cart> cartList = item.getCarts();
        cartList.add(cart);
        item.setCarts(cartList);

        ITEM_REPOSITORY.save(item);
        CART_REPOSITORY.save(cart);
        return cart;
    }
}

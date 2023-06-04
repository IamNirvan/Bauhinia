package com.nirvan.bauhinia.item;

import com.nirvan.bauhinia.cart.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/items")
public class ItemController {

    private final ItemService ITEM_SERVICE;

    @Autowired
    public ItemController(ItemService itemService) {
        ITEM_SERVICE = itemService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Item>> fetchAll() {
        return new ResponseEntity<>(ITEM_SERVICE.select(), HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Item> fetchItemById(@RequestParam(name = "itemId") int id) {
        return new ResponseEntity<>(ITEM_SERVICE.selectById(id), HttpStatus.OK);
    }

    @GetMapping("/findBySku")
    public ResponseEntity<Item> fetchItemBySku(@RequestParam(name = "sku") String sku) {
        return new ResponseEntity<>(ITEM_SERVICE.selectBySku(sku), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        Item addedItem = ITEM_SERVICE.insert(item);
        return new ResponseEntity<>(addedItem, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Item> updateItem(@RequestBody Item updatedItem) {
        Item item = ITEM_SERVICE.update(updatedItem);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @PutMapping("/{itemId}/cart/{cartId}")
    public ResponseEntity<Cart> addItemToCart(@PathVariable("itemId") int itemId, @PathVariable("cartId") int cartId) {
        Cart cart = ITEM_SERVICE.addItemToCart(itemId, cartId);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<?> deleteItem(@PathVariable("itemId") int itemId) {
        ITEM_SERVICE.delete(itemId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

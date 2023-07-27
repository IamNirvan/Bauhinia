package com.nirvan.bauhinia.item;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v2/items")
@RequiredArgsConstructor
public class ItemControllerV2 {
    private final ItemServiceV2 ITEM_SERVICE;

    /**
     * Provides information regarding all the items
     * */
    @GetMapping()
    public ResponseEntity<List<Item>> fetchAll() {
        return new ResponseEntity<>(ITEM_SERVICE.fetchAllItems(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific item
     * @param id the id of the targeted item
     * */
    @GetMapping("/find")
    public ResponseEntity<Item> fetchItemById(@RequestParam("itemId") int id) {
        return new ResponseEntity<>(ITEM_SERVICE.fetchItemById(id), HttpStatus.OK);
    }

    /**
     * provides information regarding a specific item
     * @param sku the SKU of the targeted item
     * */
    @GetMapping("/find/sku")
    public ResponseEntity<Item> fetchItemBySku(@RequestParam(name = "sku") String sku) {
        return new ResponseEntity<>(ITEM_SERVICE.fetchItemBySku(sku), HttpStatus.OK);
    }

    /**
     * Adds a new item into the system. Requires the parent (product) to be already defined and supplied
     * @param item item object
     * @param productId  id of the product the item belongs to
     * */
    @PostMapping
    public ResponseEntity<Boolean> addItem(
            @RequestBody Item item,
            @RequestParam("productId") int productId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.addItem(item, productId), HttpStatus.CREATED);
    }

    /**
     * Updates the parameters of an item, either individually or in unison
     * @param itemId id of the targeted item
     * @param sku the new, unique sku. Optional
     * @param size the new size. Optional
     * @param colour the new colour. Optional
     * @param cost the new cost. Optional
     * */
    @PutMapping()
    public ResponseEntity<Boolean> updateItem(
            @RequestParam("itemId") int itemId,
            @RequestParam(name = "sku", required = false) String sku,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "colour", required = false) String colour,
            @RequestParam(name = "cost", required = false) Double cost
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.updateItem(itemId, sku, size, colour, cost), HttpStatus.OK);
    }

    /**
     * Adds an existing item into a cart
     * @param itemId id of the targeted item
     * @param cartId id of the targeted cart
     * */
    @PutMapping("/cart")
    public ResponseEntity<Boolean> addItemToCart(
            @RequestParam("id") int itemId,
            @RequestParam("cartId") int cartId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.addItemToCart(itemId, cartId), HttpStatus.OK);
    }

    /**
     * Removes an existing item from a cart
     * @param itemId id of the targeted item
     * @param cartId id of the targeted cart
     * */
    public ResponseEntity<Boolean> removeItemFromCart(
            @RequestParam("id") int itemId,
            @RequestParam("cartId") int cartId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.removeItemFromCart(itemId, cartId), HttpStatus.OK);
    }

    /**
     * Adds an existing item into an order
     * @param itemId id of the targeted item
     * @param orderId id of the targeted order
     * */
    public ResponseEntity<Boolean> addItemToOrder(
            @RequestParam("id") int itemId,
            @RequestParam("orderId") int orderId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.addItemToOrder(itemId, orderId), HttpStatus.OK);
    }

    /**
     * Deletes an existing item
     * @param itemId id of the targeted item
     * */
    @DeleteMapping()
    public ResponseEntity<Boolean> deleteItem(@RequestParam("itemId") int itemId) {
        return new ResponseEntity<>(ITEM_SERVICE.deleteItem(itemId), HttpStatus.OK);
    }
}

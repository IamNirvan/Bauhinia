package com.nirvan.bauhinia.item;

import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/items")
@RequiredArgsConstructor
public class ItemControllerV2 {
    private final ItemServiceV2 ITEM_SERVICE;

    /**
     * Provides stats regarding the items under a specific product in the inventory
     * @param productId the product id for the items whose stats need to be collected
     * */
    @GetMapping("/stats/{productId}")
    public ResponseEntity<ItemStats> getItemStatsForProduct(@PathVariable int productId) {
        return ResponseEntity.ok(ITEM_SERVICE.getItemStatsForProduct(productId));
    }

    /**
     * Fetches the item's product using the item's SKU
     * @param sku the SKu of an existing item
     * */
    @GetMapping("/product/sku/{sku}")
    public ResponseEntity<Product> fetchProductBySku(@PathVariable("sku") String sku) {
        return ResponseEntity.ok(ITEM_SERVICE.fetchProductIdBySku(sku));
    }

    /**
     * Provides information regarding all the items
     * */
    @GetMapping()
    public ResponseEntity<List<Item>> fetchAll() {
        return new ResponseEntity<>(ITEM_SERVICE.fetchAllItems(), HttpStatus.OK);
    }

    @GetMapping("/available")
    public ResponseEntity<List<Item>> fetchAllAvailable() {
        return new ResponseEntity<>(ITEM_SERVICE.fetchAllAvailableItems(), HttpStatus.OK);
    }

    @GetMapping("/available/size/{size}")
    public ResponseEntity<List<Item>> fetchAllAvailableItemsBySize(@PathVariable("size") String size) {
        return ResponseEntity.ok(ITEM_SERVICE.fetchItemsBySize(size));
    }

    @GetMapping("/available/product/{productId}/size/{size}")
    public ResponseEntity<List<Item>> findIAllAvailableItemBySizeAndProduct(
            @PathVariable("productId") int productId,
            @PathVariable("size") String size
    ) {
        return ResponseEntity.ok(ITEM_SERVICE.findIAllAvailableItemBySizeAndProduct(size, productId));
    }

    @GetMapping("/size/{size}")
    public ResponseEntity<List<Item>> fetchItemsBySize(@PathVariable("size") String size) {
        return ResponseEntity.ok(ITEM_SERVICE.fetchItemsBySize(size));
    }




    /**
     * Provides information regarding a specific item
     * @param id the id of the targeted item
     * */
    @GetMapping("/find/item/{itemId}")
    public ResponseEntity<Item> fetchItemById(@PathVariable("itemId") int id) {
        return new ResponseEntity<>(ITEM_SERVICE.fetchItemById(id), HttpStatus.OK);
    }

    /**
     * provides information regarding a specific item
     * @param sku the SKU of the targeted item
     * */
    @GetMapping("/find/sku/{sku}")
    public ResponseEntity<Item> fetchItemBySku(@PathVariable("sku") String sku) {
        return new ResponseEntity<>(ITEM_SERVICE.fetchItemBySku(sku), HttpStatus.OK);
    }

    /**
     * Adds a new item into the system. Requires the parent (product) to be already defined and supplied
     * @param item item object
     * @param productId  id of the product the item belongs to
     * */
    @PostMapping("/{productId}")
    public ResponseEntity<Item> addItem(
            @RequestBody Item item,
            @PathVariable("productId") int productId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.addItem(item, productId), HttpStatus.CREATED);
    }

    /**
     * Assigns an image to an item
     * @param itemId the id of an existing item
     * @param image the image that must be saved
     * */
   @PostMapping("/{itemId}/image")
    public ResponseEntity<Boolean> assignImageToItem(
            @PathVariable("itemId") int itemId,
            @RequestParam("image") MultipartFile image
            ) throws IOException {
        return new ResponseEntity<>(ITEM_SERVICE.assignImageToItem(itemId, image), HttpStatus.CREATED);
    }

    /**
     * Updates the parameters of an item, either individually or in unison
     * @param itemId id of the targeted item
     * @param sku the new, unique sku. Optional
     * @param size the new size. Optional
     * @param colour the new colour. Optional
     * @param cost the new cost. Optional
     * */
    @PutMapping
    public ResponseEntity<Item> updateItem(
            @RequestParam("itemId") int itemId,
            @RequestParam(name = "sku", required = false) String sku,
            @RequestParam(name = "size", required = false) String size,
            @RequestParam(name = "colour", required = false) String colour,
            @RequestParam(name = "cost", required = false) Double cost,
            @RequestParam(name = "quantity", required = false) int quantity
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.updateItem(itemId, sku, size, colour, cost, quantity), HttpStatus.OK);
    }

    // TODO: finish this...
    @PutMapping("/{itemId}/images")
    public ResponseEntity<Boolean> updateImage(
            @PathVariable("itemId") int itemId,
            @RequestParam("image") MultipartFile file
    ) throws IOException {
        return new ResponseEntity<>(ITEM_SERVICE.updateImage(itemId, file), HttpStatus.OK);
    }

    /**
     * Adds an existing item into a cart
     * @param itemId id of the targeted item
     * @param cartId id of the targeted cart
     * */
    @PutMapping("/item/{itemId}/cart/{cartId}")
    public ResponseEntity<Cart> addItemToCart(
            @PathVariable("itemId") int itemId,
            @PathVariable("cartId") int cartId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.addItemToCart(itemId, cartId), HttpStatus.OK);
    }

    /**
     * Removes an existing item from a cart
     * @param itemIds ids of the targeted item
     * @param cartId id of the targeted cart
     * */
    @PutMapping("remove/item/{itemIds}/cart/{cartId}")
    public ResponseEntity<Boolean> removeItemFromCart(
            @PathVariable("itemIds") List<Integer> itemIds,
            @PathVariable("cartId") int cartId
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.removeItemFromCart(itemIds, cartId), HttpStatus.OK);
    }

    /**
     * Adds an existing item into an order
     * @param itemId id of the targeted item
     * @param orderId id of the targeted order
     * */
//    @PutMapping("add/item/{itemIds}/order/{orderId}/quantity/{quantity}")
    @PutMapping("/{itemIds}/order/{orderId}/quantity/{quantity}")
    public ResponseEntity<Boolean> addItemToOrder(
            @PathVariable("itemIds") List<Integer> itemId,
            @PathVariable("orderId") int orderId,
            @PathVariable("quantity") int quantity
    ) {
        return new ResponseEntity<>(ITEM_SERVICE.addItemToOrder(itemId, orderId, quantity), HttpStatus.OK);
    }
//    @PutMapping("add/item/{itemIds}/order/{orderId}")
//    public ResponseEntity<Boolean> addItemToOrder(
//            @PathVariable("itemIds") List<Integer> itemId,
//            @PathVariable("orderId") int orderId
//    ) {
//        return new ResponseEntity<>(ITEM_SERVICE.addItemToOrder(itemId, orderId), HttpStatus.OK);
//    }

    /**
     * Deletes an existing item
     * @param itemId id of the targeted item
     * */
    @DeleteMapping("/{itemId}")
    public ResponseEntity<Boolean> deleteItems(@PathVariable("itemId") int itemId) {
        return new ResponseEntity<>(ITEM_SERVICE.deleteItem(itemId), HttpStatus.OK);
    }
}

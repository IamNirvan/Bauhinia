package com.nirvan.bauhinia.product;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/products")
@RequiredArgsConstructor
@CrossOrigin
public class ProductControllerV2 {
    private final ProductServiceV2 PRODUCT_SERVICE;

    @GetMapping("product/{product}/manufacturer/{manufacturer}")
    public ResponseEntity<Boolean> existsByName(
            @PathVariable("product") String productName,
            @PathVariable("manufacturer") String manufacturerName
    ){
        return new ResponseEntity<>(PRODUCT_SERVICE.existsByName(productName, manufacturerName), HttpStatus.OK);
    }

    /**
     * Provides information regarding all products
     * */
    @GetMapping
    public ResponseEntity<List<Product>> fetchAllProducts(){
        return new ResponseEntity<>(PRODUCT_SERVICE.fetchAllProducts(), HttpStatus.OK);
    }

    /**
     * Provides information regarding a specific product
     * @param orderId id of the targeted product order
     * */
    @GetMapping("/find/{id}")
    public ResponseEntity<Product> fetchProductById(@PathVariable("id") int orderId) {
        return new ResponseEntity<>(PRODUCT_SERVICE.fetchProductById(orderId), HttpStatus.OK);
    }

    /**
     * Adds a new product into the system
     * @param product a product object
     * @param categoryIds a list of category ids that need to be associated with this product
     *
     * */
    @PostMapping
    public ResponseEntity<Product> addProduct(
            @RequestBody Product product,
            @RequestParam(name = "catId", required = false) List<Integer> categoryIds
    ) {
        return new ResponseEntity<>(PRODUCT_SERVICE.addProduct(product, categoryIds), HttpStatus.CREATED);
    }

    /**
     * Only updates the general information of a product excluding the category ids
     * @param productId id of the targeted product
     * @param manufacturerName new name of the manufacturer. Optional
     * @param productName new name of the product. Optional
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateProduct(
            @RequestParam("id") int productId,
            @RequestParam(name = "manufacturer", required = false) String manufacturerName,
            @RequestParam(name = "product", required = false) String productName
    ) {
        return new ResponseEntity<>(
                PRODUCT_SERVICE.updateProduct(productId, manufacturerName, productName),
                HttpStatus.OK
        );
    }

    /**
     * Adds the product under a new category and vice versa
     * @param productId id of the targeted product
     * @param categoryIds list of the category ids to add
     * */
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<Boolean> assignCategoryToProduct(
            @PathVariable("productId") int productId,
            @PathVariable("categoryId") List<Integer> categoryIds
    ) {
        return new ResponseEntity<>(PRODUCT_SERVICE.assignCategoryToProduct(productId, categoryIds), HttpStatus.OK);
    }

    /**
     * Removes a category id(s) from the product and vice versa
     * @param productId id of the targeted product
     * @param categoryIds list of the category ids to remove
     * */
    @PutMapping("{productId}/remove/category/{categoryId}")
    public ResponseEntity<Boolean> removeCategoryFromProduct(
            @PathVariable("productId") int productId,
            @PathVariable("categoryId") List<Integer> categoryIds
    ) {
        return new ResponseEntity<>(PRODUCT_SERVICE.removeCategoryFromProduct(productId, categoryIds), HttpStatus.OK);
    }

    /**
     * Deletes an existing product
     * @param productId id of the targeted product
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("id") int productId) {
        return new ResponseEntity<>(PRODUCT_SERVICE.deleteProduct(productId), HttpStatus.OK);
    }
}

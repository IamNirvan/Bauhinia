package com.nirvan.bauhinia.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/products")
public class ProductController {

    private final ProductService SERVICE;

    @Autowired
    public ProductController(ProductService service) {
        SERVICE = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> fetchAllProducts(){
        List<Product> products = SERVICE.select();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Product> findProductById(@RequestParam(name = "productId") int id) {
        return new ResponseEntity<>(SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping("/insert")
    public ResponseEntity addProduct(@RequestBody Product product) {
        Product addedProduct = SERVICE.insert(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    // This is used to assign a product to a category
    @PutMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<Product> assignCategoryToProduct(
            @PathVariable("productId") int productId,
            @PathVariable("categoryId") int categoryId) {

        SERVICE.assignCategoryToProduct(productId, categoryId);

        return null;
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") int productId) {
        SERVICE.delete(productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

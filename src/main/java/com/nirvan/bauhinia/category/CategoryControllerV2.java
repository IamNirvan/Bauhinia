package com.nirvan.bauhinia.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/categories")
@RequiredArgsConstructor
@CrossOrigin
public class CategoryControllerV2 {
    private final CategoryServiceV2 CATEGORY_SERVICE;

    /**
     * Use this method to get the basic info regarding all the categories
     * */
    @GetMapping
    public ResponseEntity<List<Category>> fetchAllCategories() {
        return new ResponseEntity<>(CATEGORY_SERVICE.fetchAllCategories(), HttpStatus.OK);
    }

    /**
     * Use this method to get the basic info regarding a single existing category
     * @param categoryId the id of the category
     * */
    @GetMapping("/find/{id}")
    public ResponseEntity<Category> fetchCategoryById(@PathVariable("id") int categoryId) {
        return new ResponseEntity<>(CATEGORY_SERVICE.fetchCategoryById(categoryId), HttpStatus.OK);
    }

    /**
     * Provides a category after searching for it by its name
     * @param name the name of an existing category
     * */
    @GetMapping("/find/name/{name}")
    public ResponseEntity<Category> fetchCategoryByName(@PathVariable("name") String name) {
        return new ResponseEntity<>(CATEGORY_SERVICE.fetchCategoryByName(name), HttpStatus.OK);
    }

    /**
     * Use this method to add a new category that does not already exist
     * @param category the new category name
     * */
    @PostMapping
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        return new ResponseEntity<>(CATEGORY_SERVICE.addCategory(category), HttpStatus.OK);
    }

    /**
     * Use this method to update an existing category
     * @param categoryId the id of an existing category
     * @param categoryName the new name of the category
     * */
    @PutMapping("/{id}/name/{name}")
    public ResponseEntity<Boolean> updateCategory(
            @PathVariable("id") int categoryId,
            @PathVariable("name") String categoryName
    ) {
        return new ResponseEntity<>(CATEGORY_SERVICE.updateCategory(categoryId, categoryName), HttpStatus.OK);
    }

    @PutMapping("/clear/{id}")
    public ResponseEntity<Category> clearProducts(@PathVariable("id") int categoryId) {
        return ResponseEntity.ok(CATEGORY_SERVICE.clearProducts(categoryId));
    }

    /**
     * Use this method to delete an existing category
     * @param categoryId the id of an existing category
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("id") int categoryId) {
        return new ResponseEntity<>(CATEGORY_SERVICE.deleteCategory(categoryId), HttpStatus.OK);
    }
}


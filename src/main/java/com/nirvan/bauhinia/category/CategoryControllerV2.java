package com.nirvan.bauhinia.category;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v2/categories")
@RequiredArgsConstructor
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
    @GetMapping("/find")
    public ResponseEntity<Category> fetchCategoryById(int categoryId) {
        return new ResponseEntity<>(CATEGORY_SERVICE.fetchCategoryById(categoryId), HttpStatus.OK);
    }

    /**
     * Use this method to add a new category that does not already exist
     * @param category the new category name
     * */
    @PostMapping
    public ResponseEntity<Boolean> addCategory(@RequestBody Category category) {
        return new ResponseEntity<>(CATEGORY_SERVICE.addCategory(category), HttpStatus.OK);
    }

    /**
     * Use this method to update an existing category
     * @param categoryId the id of an existing category
     * @param categoryName the new name of the category
     * */
    @PutMapping
    public ResponseEntity<Boolean> updateCategory(
            @RequestParam("id") int categoryId,
            @RequestParam("name") String categoryName
    ) {
        return new ResponseEntity<>(CATEGORY_SERVICE.updateCategory(categoryId, categoryName), HttpStatus.OK);
    }

    /**
     * Use this method to delete an existing category
     * @param categoryId the id of an existing category
     * */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteCategory(@RequestParam("id") int categoryId) {
        return new ResponseEntity<>(CATEGORY_SERVICE.deleteCategory(categoryId), HttpStatus.OK);
    }
}


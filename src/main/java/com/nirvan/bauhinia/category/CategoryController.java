package com.nirvan.bauhinia.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/categories")
public class CategoryController {

    private final CategoryService SERVICE;

    @Autowired
    public CategoryController(CategoryService service) {
        SERVICE = service;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Category>> fetchAllCategories() {
        List<Category> categories = SERVICE.select();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("/find")
    public ResponseEntity<Category> findCategoryById(@RequestParam("categoryId") int id) {
        return new ResponseEntity<>(SERVICE.selectById(id), HttpStatus.OK);
    }

    @PostMapping(path = "/insert")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category addeddCategory = SERVICE.insert(category);
        return new ResponseEntity<>(addeddCategory, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") int categoryId) {
        SERVICE.delete(categoryId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}

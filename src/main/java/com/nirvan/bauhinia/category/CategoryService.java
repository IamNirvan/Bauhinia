package com.nirvan.bauhinia.category;

import com.nirvan.bauhinia.exception.CategoryNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CategoryService {

    private final CategoryRepository REPOSITORY;

    @Autowired
    public CategoryService(CategoryRepository repository) {
        REPOSITORY = repository;
    }

    public List<Category> select() {
        return REPOSITORY.findAll();
    }

    public Category selectById(int id) {
        return REPOSITORY.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id (" + id + ") does not exist"));
    }

    public Category insert(Category category) {
        // Check if the name is valid
        if(category.getName().length() == 0) {
            throw new InvalidParameterException("Category name (" +category.getName()+ ") is invalid");
        }

        REPOSITORY.save(category);
        return category;
    }

    public Category update(Category updatedCategory) {
        Category persistedCategory = REPOSITORY.findById(updatedCategory.getId())
                .orElseThrow(() -> new CategoryNotFoundException("Category with id (" + updatedCategory.getId() + ") does not exist"));

        // Check if the name is valid
        if(updatedCategory.getName().length() == 0) {
            if(!Objects.equals(persistedCategory.getName(), updatedCategory.getName())) {
                persistedCategory.setName(updatedCategory.getName());
            }
            throw new InvalidParameterException("Category name (" +updatedCategory.getName()+ ") is invalid");
        }

        return persistedCategory;
    }

    public boolean delete(int categoryId) {
        if(!REPOSITORY.existsById(categoryId)) {
            throw new CategoryNotFoundException("Category with id (" + categoryId + ") does not exist");
        }
        REPOSITORY.deleteById(categoryId);
        return true;
    }
}

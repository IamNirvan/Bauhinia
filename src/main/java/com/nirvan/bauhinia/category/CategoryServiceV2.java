package com.nirvan.bauhinia.category;

import com.nirvan.bauhinia.exception.CategoryNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.utility.Validation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceV2 {
    private final CategoryRepository CATEGORY_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Category with the following id does not exist: %s";
    private static final String DUPLICATE_NAME_MESSAGE = "Category name already exists: %s";
    private static final String INVALID_NAME_MESSAGE = "Category name is invalid: %s";

    public List<Category> fetchAllCategories() {
        return CATEGORY_REPOSITORY.findAll();
    }

    public Category fetchCategoryById(int categoryId) {
        return CATEGORY_REPOSITORY.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, categoryId)));
    }

    public Boolean addCategory(Category category) {
        final String CATEGORY_NAME = category.getName();
        //
        // Check if the category name is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(CATEGORY_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_NAME_MESSAGE, CATEGORY_NAME));
        }
        if(CATEGORY_REPOSITORY.existsCategoryByName(CATEGORY_NAME)) {
            throw new InvalidParameterException(String.format(DUPLICATE_NAME_MESSAGE, CATEGORY_NAME));
        }
        CATEGORY_REPOSITORY.save(category);
        return true;
    }

    public Boolean updateCategory(int categoryId, String categoryName) {
        final Category PERSISTED_CATEGORY =fetchCategoryById(categoryId);
        //
        // Check if the name is valid
        //
        if(categoryName != null) {
            if(!VALIDATION.validNonBlankParam(categoryName)) {
                throw new InvalidParameterException(String.format(INVALID_NAME_MESSAGE, categoryName));
            }
            if(CATEGORY_REPOSITORY.existsCategoryByName(categoryName) && !PERSISTED_CATEGORY.getName().equals(categoryName)) {
                throw new InvalidParameterException(String.format(DUPLICATE_NAME_MESSAGE, categoryName));
            }
            PERSISTED_CATEGORY.setName(categoryName);
        }
        CATEGORY_REPOSITORY.save(PERSISTED_CATEGORY);
        return true;
    }

    public Boolean deleteCategory(int categoryId) {
        if(!CATEGORY_REPOSITORY.existsById(categoryId)) {
            throw new CategoryNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, categoryId));
        }
        CATEGORY_REPOSITORY.deleteById(categoryId);
        return true;
    }

    public void save(Category category) {
        CATEGORY_REPOSITORY.save(category);
    }
}

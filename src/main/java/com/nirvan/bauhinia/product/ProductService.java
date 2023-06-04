package com.nirvan.bauhinia.product;

import com.nirvan.bauhinia.category.Category;
import com.nirvan.bauhinia.category.CategoryRepository;
import com.nirvan.bauhinia.exception.CategoryNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ProductService {

    private final ProductRepository PRODUCT_REPOSITORY;
    private final CategoryRepository CATEGORY_REPOSITORY;

    @Autowired
    public ProductService(ProductRepository repository, CategoryRepository categoryRepository) {
        PRODUCT_REPOSITORY = repository;
        CATEGORY_REPOSITORY = categoryRepository;
    }

    public List<Product> select() {
        return PRODUCT_REPOSITORY.findAll();
    }

    public Product selectById(int productId) {
        return PRODUCT_REPOSITORY.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") does not exist"));
    }

    public Product insert(Product product) {
        // Check if the manufacturer is valid
        if(product.getManufacturer().length() == 0) {
            throw new InvalidParameterException("Manufacturer (" +product.getManufacturer()+ ") is invalid");
        }

        // Check if the name is valid
        if(product.getProductName().length() == 0) {
            throw new InvalidParameterException("Product name (" +product.getProductName()+ ") is invalid");
        }

        PRODUCT_REPOSITORY.save(product);
        return product;
    }

    @Transactional
    public Product update(Product updatedProduct) {
        Product persistedProduct = PRODUCT_REPOSITORY.findById(updatedProduct.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" +updatedProduct.getId()+ ") does not exist"));

        // Check if the manufacturer is valid
        if(updatedProduct.getManufacturer().length() != 0) {
            if(!Objects.equals(persistedProduct.getManufacturer(), updatedProduct.getManufacturer())) {
                persistedProduct.setManufacturer(updatedProduct.getManufacturer());
            }
        }
        else {
            throw new InvalidParameterException("Manufacturer (" +updatedProduct.getManufacturer()+ ") is invalid");
        }

        // Check if the product name is valid
        if(updatedProduct.getProductName().length() != 0) {
            if(!Objects.equals(persistedProduct.getProductName(), updatedProduct.getProductName())) {
                persistedProduct.setProductName(updatedProduct.getProductName());
            }
        }
        else {
            throw new InvalidParameterException("Product name (" +updatedProduct.getProductName()+ ") is invalid");
        }

        return persistedProduct;
    }

    public boolean delete(int productId) {
        if(!PRODUCT_REPOSITORY.existsById(productId)) {
            throw new ProductNotFoundException("Product with id (" + productId + ") does not exist");
        }
        PRODUCT_REPOSITORY.deleteById(productId);
        return true;
    }

    public Product assignCategoryToProduct(int productId, int categoryId) {
        List<Category> productCategories= null;

        // Fetch the product and category from the DB
        Product product = PRODUCT_REPOSITORY.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product with id (" + productId + ") does not exist"));

        Category category = CATEGORY_REPOSITORY.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException("Category with id (" + categoryId + ") does not exist"));

        productCategories = product.getCategories();
        productCategories.add(category);
        product.setCategories(productCategories);
        PRODUCT_REPOSITORY.save(product);

        return product;
    }
}

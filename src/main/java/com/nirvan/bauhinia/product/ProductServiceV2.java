package com.nirvan.bauhinia.product;

import com.nirvan.bauhinia.category.Category;
import com.nirvan.bauhinia.category.CategoryServiceV2;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ProductNotFoundException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceV2 {
    private final ProductRepository PRODUCT_REPOSITORY;
    private final CategoryServiceV2 CATEGORY_SERVICE;
    private final Validation validation;
    private static final String ID_NOT_FOUND_MESSAGE = "Product with the following id does not exist: %s";
    private static final String INVALID_MANUFACTURER_MESSAGE = "Manufacturer name is invalid: %s";
    private static final String INVALID_PRODUCT_NAME_MESSAGE = "Product name is invalid: %s";

    public List<Product> fetchAllProducts() {
        return PRODUCT_REPOSITORY.findAll();
    }

    public Boolean existsByName(String productName, String manufacturerName) {
        return PRODUCT_REPOSITORY.existsByProductName(productName) && PRODUCT_REPOSITORY.existsByManufacturer(manufacturerName);
    }

    public Product fetchProductById(int productId) throws ProductNotFoundException {
        return PRODUCT_REPOSITORY.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, productId)));
    }

    public Product addProduct(@NotNull Product product, List<Integer> categoryIds) throws InvalidParameterException {
        final String manufacturerName = product.getManufacturer();
        final String productName = product.getProductName();

        if(!validation.validNonNullAndNonBlankParam(manufacturerName)) {
            throw new InvalidParameterException(String.format(INVALID_MANUFACTURER_MESSAGE, manufacturerName));
        }

        if(!validation.validNonNullAndNonBlankParam(productName)) {
            throw new InvalidParameterException(String.format(INVALID_PRODUCT_NAME_MESSAGE, productName));
        }
        PRODUCT_REPOSITORY.save(product);
        if(categoryIds != null) {
            assignCategoryToProduct(product.getId(), categoryIds);

        }
        PRODUCT_REPOSITORY.save(product);
        return product;
    }

    @Transactional
    public Boolean updateProduct(
            int productId,
            String manufacturerName,
            String productName
    ) throws InvalidParameterException {
        Product persistedProduct = fetchProductById(productId);

        if(manufacturerName != null) {
            if(!validation.validNonBlankParam(manufacturerName)) {
                throw new InvalidParameterException(String.format(INVALID_MANUFACTURER_MESSAGE, manufacturerName));
            }
            persistedProduct.setManufacturer(manufacturerName);
        }

        if(productName != null) {
            if(!validation.validNonBlankParam(productName)) {
                throw new InvalidParameterException(String.format(INVALID_PRODUCT_NAME_MESSAGE, productName));
            }
            persistedProduct.setProductName(productName);
        }
        PRODUCT_REPOSITORY.save(persistedProduct);
        return true;
    }

    @Transactional
    public Boolean assignCategoryToProduct(int productId, List<Integer> categoryIds) {
        for (Integer categoryId: categoryIds) {
            Product product = fetchProductById(productId);
            Category category = CATEGORY_SERVICE.fetchCategoryById(categoryId);

            List<Category> productCategories= product.getCategories();
            productCategories.add(category);
            product.setCategories(productCategories);

            List<Product> productList = category.getProducts();
            productList.add(product);
            category.setProducts(productList);

            PRODUCT_REPOSITORY.save(product);
        }
        return true;
    }

    @Transactional
    public Boolean removeCategoryFromProduct(int productId, List<Integer> categoryIds) {
        final Product product = fetchProductById(productId);
        final List<Category> categoryList = product.getCategories();

        categoryIds.forEach((categoryId) -> {
            Category category = CATEGORY_SERVICE.fetchCategoryById(categoryId);
            categoryList.remove(category);
        });
        product.setCategories(categoryList);

        categoryIds.forEach((categoryId) -> {
            Category category = CATEGORY_SERVICE.fetchCategoryById(categoryId);
            List<Product> productlist = category.getProducts();
            productlist.remove(product);
            category.setProducts(productlist);
        });

        PRODUCT_REPOSITORY.save(product);
        return true;
    }

    public Boolean deleteProduct(int productId) {
        if(!PRODUCT_REPOSITORY.existsById(productId)) {
            throw new ProductNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, productId));
        }
        PRODUCT_REPOSITORY.deleteById(productId);
        return true;
    }

    public void saveProduct(Product product) {
        PRODUCT_REPOSITORY.save(product);
    }
}

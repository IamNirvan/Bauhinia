package com.nirvan.bauhinia.product;


import com.nirvan.bauhinia.category.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Product")
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @ManyToMany
    @JoinTable(name = "product_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;

    public Product(String manufacturer, String productName) {
        this.manufacturer = manufacturer;
        this.productName = productName;
        this.categories = new ArrayList<>();
    }
}

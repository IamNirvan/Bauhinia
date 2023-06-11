package com.nirvan.bauhinia.product;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.category.Category;
import com.nirvan.bauhinia.item.Item;
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

    @ManyToMany(mappedBy = "products")
    @JsonIgnore
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "product")
    private List<Item> itemList = new ArrayList<>();

    public Product(String manufacturer, String productName) {
        this.manufacturer = manufacturer;
        this.productName = productName;
    }

    public Product(String manufacturer, String productName, List<Category> categories) {
        this.manufacturer = manufacturer;
        this.productName = productName;
        this.categories = categories;
    }
}

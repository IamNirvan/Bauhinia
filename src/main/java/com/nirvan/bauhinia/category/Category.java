package com.nirvan.bauhinia.category;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Category")
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @JsonIgnore // To prevent a circular reference
    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    public Category(String name) {
        this.name = name;
    }
}

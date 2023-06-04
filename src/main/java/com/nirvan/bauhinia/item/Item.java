package com.nirvan.bauhinia.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.cart.Cart;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Item")
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "sku", nullable = false)
    private String sku;

    @Column(name = "size", nullable = false)
    private String size;

    @Column(name = "colour", nullable = false)
    private String colour;

    @Column(name = "cost", nullable = false)
    private double cost;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    private List<Cart> carts;

    public Item(String sku, String size, String colour, double cost) {
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
    }
}

package com.nirvan.bauhinia.cart;

import com.nirvan.bauhinia.item.Item;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Cart")
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue
    private int id;

    @ManyToMany()
    @JoinTable(name = "cart_items",
            joinColumns = @JoinColumn(name = "cart_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<Item> items;

}

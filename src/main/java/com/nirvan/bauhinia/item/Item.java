package com.nirvan.bauhinia.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nirvan.bauhinia.imagedata.ImageData;
import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.order.ProductOrder;
import com.nirvan.bauhinia.product.Product;
import com.nirvan.bauhinia.review.Review;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Item")
@Table(name = "item", uniqueConstraints = {
        @UniqueConstraint(name = "sku_unique", columnNames = "sku")
})
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

//    @Column(name = "imageItem")
    @OneToMany(mappedBy = "imageItem", cascade = CascadeType.ALL)
    private List<ImageData> images;

    @ManyToMany(mappedBy = "items")
    @JsonIgnore
    private List<Cart> carts;

    @OneToMany(mappedBy = "item", cascade = CascadeType.REMOVE)
    private List<Review> reviews;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(columnDefinition = "Integer default null")
    private ProductOrder order;

    @ManyToOne
    @JsonIgnore
//    @JsonManagedReference
    @JoinColumn(name="product_id_fk")
    private Product product;


    // TODO: this one...
    @Column(name = "quantity")
    private int quantity;

    public Item(String sku, String size, String colour, double cost) {
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
    }

    public Item(String sku, String size, String colour, double cost, int quantity) {
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
        this.quantity = quantity;
    }

    public Item(String sku, String size, String colour, double cost,
                List<ImageData> images, Product product) {
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
        this.images = images;
        this.product = product;
    }

    public Item(String sku, String size, String colour, double cost,
                List<ImageData> images, Product product, int quantity) {
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
        this.images = images;
        this.product = product;
        this.quantity = quantity;
    }

    public Item(int id, String sku, String size, String colour, double cost, Product product) {
        this.id = id;
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
        this.product = product;
    }

    public Item(int id, String sku, String size, String colour, double cost, int quantity, Product product) {
        this.id = id;
        this.sku = sku;
        this.size = size;
        this.colour = colour;
        this.cost = cost;
        this.quantity = quantity;
        this.product = product;
    }
}

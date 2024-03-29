package com.nirvan.bauhinia.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.item.Item;
import com.nirvan.bauhinia.orderAddress.OrderAddress;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "ProductOrder")
@Table(name = "product_order")
public class ProductOrder {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<Item> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderAddress> addresses;

    public ProductOrder(double price, LocalDate date) {
        this.price = price;
        this.date = date;
    }

    public ProductOrder(double price, LocalDate date, List<Item> items, List<OrderAddress> addresses) {
        this.price = price;
        this.date = date;
        this.items = items;
        this.addresses = addresses;
    }
}

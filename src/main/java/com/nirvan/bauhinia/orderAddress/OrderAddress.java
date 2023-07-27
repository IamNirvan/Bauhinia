package com.nirvan.bauhinia.orderAddress;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.order.ProductOrder;
import com.nirvan.bauhinia.product.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "OrderAddress")
@Table(name = "order_address")
public class OrderAddress {
    @Id
    @GeneratedValue
    private int id;

    @Column(name = "province", nullable = false)
    private String province;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street_name", nullable = false)
    private String streetName;

    @Column(name = "building_number", nullable = false)
    private String buildingNumber;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    @Column(name = "address", nullable = false)
    private String addressType;

    @ManyToOne
    @JsonIgnore
    private ProductOrder order;

    public OrderAddress(String province, String city, String streetName, String buildingNumber, String postalCode,
                        String addressType) {
        this.province = province;
        this.city = city;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.addressType = addressType;
    }
}



package com.nirvan.bauhinia.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.customer.Customer;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity(name = "Address")
@Table(name = "address")
public class Address {
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

    @Enumerated(EnumType.STRING)
    private AddressType addressType;

    @ManyToOne
    @JoinColumn(name = "customer_id_fk")
    @JsonIgnore
    private Customer customer;

    public Address(String province, String city, String streetName, String buildingNumber, String postalCode, AddressType type) {
        this.province = province;
        this.city = city;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
        this.addressType = type;
    }
}

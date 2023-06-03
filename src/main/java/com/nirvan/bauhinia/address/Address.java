package com.nirvan.bauhinia.address;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
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

    @Column(name = "nuilding_number", nullable = false)
    private String buildingNumber;

    @Column(name = "postal_code", nullable = false)
    private String postalCode;

    public Address(String province, String city, String streetName, String buildingNumber, String postalCode) {
        this.province = province;
        this.city = city;
        this.streetName = streetName;
        this.buildingNumber = buildingNumber;
        this.postalCode = postalCode;
    }
}

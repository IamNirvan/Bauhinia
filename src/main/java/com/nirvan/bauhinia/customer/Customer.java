package com.nirvan.bauhinia.customer;

import com.nirvan.bauhinia.address.Address;
import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.order.ProductOrder;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Customer")
@Table(name = "customer", uniqueConstraints = { @UniqueConstraint(name = "email_unique", columnNames = "email")})
public class Customer implements Serializable {

    @Id
    @GeneratedValue
    private int id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "contact_number_1", nullable = false)
    private String contactNumber1;

    @Column(name = "contact_number_2", nullable = false)
    private String contactNumber2;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id_fk")
    private List<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
    private List<ProductOrder> orders;

    public Customer(String firstName, String lastName, String email, String password, String contactNumber1,
                    String contactNumber2) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber1 = contactNumber1;
        this.contactNumber2 = contactNumber2;
        this.cart = new Cart();
    }

    public Customer(String firstName, String lastName, String email, String password, String contactNumber1,
                    String contactNumber2, List<Address> addresses) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber1 = contactNumber1;
        this.contactNumber2 = contactNumber2;
        this.addresses = addresses;
        this.cart = new Cart();
    }

    public Customer(int id, String firstName, String lastName, String email, String password, String contactNumber1,
                    String contactNumber2, Cart cart) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber1 = contactNumber1;
        this.contactNumber2 = contactNumber2;
        this.cart = cart;
    }

    public Customer(String firstName, String lastName, String email, String password, String contactNumber1,
                    String contactNumber2, List<Address> addresses, Cart cart) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber1 = contactNumber1;
        this.contactNumber2 = contactNumber2;
        this.addresses = addresses;
        this.cart = cart;
    }
}


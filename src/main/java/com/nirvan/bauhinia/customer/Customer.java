package com.nirvan.bauhinia.customer;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nirvan.bauhinia.address.Address;
import com.nirvan.bauhinia.cart.Cart;
import com.nirvan.bauhinia.order.ProductOrder;
import com.nirvan.bauhinia.review.Review;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Customer")
@Table(name = "customer", uniqueConstraints = { @UniqueConstraint(name = "email_unique", columnNames = "email")})
public class Customer {

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

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Address> addresses;

    @OneToOne(cascade = CascadeType.ALL)
    private Cart cart = new Cart();

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "customer")
    private List<ProductOrder> orders;

    /**
     * This constructor will be helpful when logging the user into the system.
     * @param email user's registered email address
     * @param password a valid password
     * */
    public Customer(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * This constructor will be helpful when registering a user.
     * The address can be created separately and stored; therefore,
     * this constructor does not require the address
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email address. This will be used instead of a username
     * @param password a valid password
     * @param contactNumber1 the first working contact number
     * @param contactNumber2 the second working contact number
     * */
    public Customer(
            String firstName,
            String lastName,
            String email,
            String password,
            String contactNumber1,
            String contactNumber2
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber1 = contactNumber1;
        this.contactNumber2 = contactNumber2;
    }

    /**
     * This constructor will be helpful when registering a user.
     * When registering, the user must provide the first name, last name,
     * email address, password, 2 contact numbers, a billing address and
     * shipping address.
     * @param firstName user's first name
     * @param lastName user's last name
     * @param email user's email address. This will be used instead of a username
     * @param password a valid password
     * @param contactNumber1 the first working contact number
     * @param contactNumber2 the second working contact number
     * @param addresses a list of address which includes the shipping and billing address
     * */
    public Customer(
            String firstName,
            String lastName,
            String email,
            String password,
            String contactNumber1,
            String contactNumber2,
            List<Address> addresses
    ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.contactNumber1 = contactNumber1;
        this.contactNumber2 = contactNumber2;
        this.addresses = addresses;
    }
}


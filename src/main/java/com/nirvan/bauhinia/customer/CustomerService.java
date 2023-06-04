package com.nirvan.bauhinia.customer;

import com.nirvan.bauhinia.address.AddressService;
import com.nirvan.bauhinia.exception.CustomerNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository REPOSITORY;
    private final AddressService ADDRESS_SERVICE;

    @Autowired
    public CustomerService(CustomerRepository repository, AddressService addressService) {
        REPOSITORY = repository;
        ADDRESS_SERVICE = addressService;
    }

    public List<Customer> select() {
        return REPOSITORY.findAll();
    }

    public Customer insert(Customer customer) {
        // Make sure the first name is valid
        if(customer.getFirstName().length() == 0) {
            throw new InvalidParameterException("First name (" + customer.getFirstName() + ") is invalid");
        }

        // Make sure the last name is valid
        if(customer.getLastName().length() == 0) {
            throw new InvalidParameterException("Last name (" + customer.getLastName() + ") is invalid");
        }

        // Make sure the password is valid
        if(customer.getPassword().length() < 8) {
            throw new InvalidParameterException("Password is invalid");
        }

        // Make sure the email is valid and that it is unique
        if(customer.getEmail().length() == 0) {
            throw new InvalidParameterException("Email ( " + customer.getEmail() + ") is invalid");
        }
        else if(REPOSITORY.existsCustomerByEmail(customer.getEmail())) {
            throw new InvalidParameterException("Email ( " + customer.getEmail() + ") is taken");
        }

        // Make sure the contactNumbers are unique and are valid
        if(customer.getContactNumber1().length() == 0) {
            throw new InvalidParameterException("Contact number (" + customer.getContactNumber1() + ") is invalid");
        }
        else if (REPOSITORY.findCustomerByContactNumber1(customer.getContactNumber1()).isPresent()) {
            System.out.println(REPOSITORY.findCustomerByContactNumber1(customer.getContactNumber1()).isPresent());
            throw new InvalidParameterException("Contact number (" + customer.getContactNumber1() + ") is taken");
        }

        if(customer.getContactNumber2().length() == 0) {
            throw new InvalidParameterException("Contact number (" + customer.getContactNumber2() + ") is invalid");
        }
        else if (REPOSITORY.findCustomerByContactNumber2(customer.getContactNumber2()).isPresent()) {
            throw new InvalidParameterException("Contact number (" + customer.getContactNumber2() + ") is taken");
        }

        // Validate address
        if (customer.getAddresses() == null) {
            throw new NullPointerException("Addresses cannot be null");
        }
        customer.getAddresses().forEach(ADDRESS_SERVICE::validateAddress);

        // Contact numbers and email are valid
        REPOSITORY.save(customer);
        return customer;
    }

    public Customer findCustomerById(int id) {
        return REPOSITORY.findById(id).
                orElseThrow(() -> new CustomerNotFoundException("Customer with id ("+ id +") was not found"));
    }

    @Transactional
    public Customer updateExcludingAddress(Customer updatedCustomer) {
        Customer persistedCustomer = REPOSITORY.findById(updatedCustomer.getId()).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id ("+ updatedCustomer.getId() +") does not exist"));

        // Make sure the first name is valid
        if(updatedCustomer.getFirstName().length() != 0) {
            if(!Objects.equals(persistedCustomer.getFirstName(), updatedCustomer.getFirstName())){
                persistedCustomer.setFirstName(updatedCustomer.getFirstName());
            }
        }
        else {
            throw new InvalidParameterException("First name (" + updatedCustomer.getFirstName() + ") is invalid");
        }

        // Make sure the last name is valid
        if(updatedCustomer.getLastName().length() != 0) {
            if(!Objects.equals(persistedCustomer.getLastName(), updatedCustomer.getLastName())) {
                persistedCustomer.setLastName(updatedCustomer.getLastName());
            }
        }
        else {
            throw new InvalidParameterException("Last name (" + updatedCustomer.getLastName() + ") is invalid");
        }

        // Make sure the password is valid
        if(updatedCustomer.getPassword().length() >= 8) {
            if(!Objects.equals(persistedCustomer.getPassword(), updatedCustomer.getPassword())) {
                persistedCustomer.setPassword(updatedCustomer.getPassword());
            }
        }
        else {
            throw new InvalidParameterException("Password is invalid");
        }

        // Make sure the email is valid and that it is unique
        if(updatedCustomer.getEmail().length() != 0) {
            if(!Objects.equals(persistedCustomer.getEmail(), updatedCustomer.getEmail())) {
                if(REPOSITORY.existsCustomerByEmail(updatedCustomer.getEmail())) {
                    throw new InvalidParameterException("Email address (" + updatedCustomer.getEmail() + ") is taken");
                }
                persistedCustomer.setEmail(updatedCustomer.getEmail());
            }
        } else {
            throw new InvalidParameterException("Email address (" + updatedCustomer.getEmail() + ") is invalid");
        }

        // Make sure the contactNumbers are unique and are valid
        if (updatedCustomer.getContactNumber1().length() != 0) {
            if (!Objects.equals(persistedCustomer.getContactNumber1(), updatedCustomer.getContactNumber1())) {
                if (REPOSITORY.findCustomerByContactNumber1(updatedCustomer.getContactNumber1()).isPresent()) {
                    throw new InvalidParameterException("Contact number (" + updatedCustomer.getContactNumber1() + ") is taken");
                }
                persistedCustomer.setContactNumber1(updatedCustomer.getContactNumber1());
            }
        } else {
            throw new InvalidParameterException("Contact number (" + updatedCustomer.getContactNumber1() + ") is invalid");
        }

        if (updatedCustomer.getContactNumber2().length() != 0) {
            if (!Objects.equals(persistedCustomer.getContactNumber2(), updatedCustomer.getContactNumber2())) {
                if (REPOSITORY.findCustomerByContactNumber2(updatedCustomer.getContactNumber2()).isPresent()) {
                    throw new InvalidParameterException("Contact number (" + updatedCustomer.getContactNumber2() + ") is taken");
                }
                persistedCustomer.setContactNumber2(updatedCustomer.getContactNumber2());
            }
        } else {
            throw new InvalidParameterException("Contact number (" + updatedCustomer.getContactNumber2() + ") is invalid");
        }

        // Validate address
        return persistedCustomer;
    }

    @Transactional
    public Customer update(Customer updatedCustomer) {
        Customer persistedCustomer = REPOSITORY.findById(updatedCustomer.getId()).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id ("+ updatedCustomer.getId() +") does not exist"));

        updateExcludingAddress(updatedCustomer);

        // Validate address
        updatedCustomer.getAddresses().forEach(ADDRESS_SERVICE::validateAddress);
        persistedCustomer.setAddresses(updatedCustomer.getAddresses());

        return persistedCustomer;
    }

    public boolean delete(int customerId) {
        if(!REPOSITORY.existsById(customerId)) {
            throw new CustomerNotFoundException("Customer with id (" + customerId + ") does not exist");
        }
        REPOSITORY.deleteById(customerId);
        return true;
    }
}

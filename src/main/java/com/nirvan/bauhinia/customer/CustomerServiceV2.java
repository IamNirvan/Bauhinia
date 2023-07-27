package com.nirvan.bauhinia.customer;

import com.nirvan.bauhinia.exception.CustomerNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.WeakPasswordException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceV2 {
    private final CustomerRepository CUSTOMER_REPOSITORY;
    private final Validation validation;
    private static final String ID_NOT_FOUND_MESSAGE = "Customer with the following id does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s. Make sure to include " +
            "at least 8 characters. 1 uppercase character, 1 lowercase character and 1 special character";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String DUPLICATE_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_CONTACT_NUMBER_MESSAGE = "Contact number is invalid: %s";
    private static final String DUPLICATE_CONTACT_NUMBER_MESSAGE = "Contact number is taken: %s";

    public List<Customer> fetchAllCustomers() {
        return CUSTOMER_REPOSITORY.findAll();
    }

    public Customer fetchCustomerById(int customerId) throws CustomerNotFoundException {
        return CUSTOMER_REPOSITORY.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, customerId)));
    }

    public boolean addCustomer(@NotNull Customer customer) throws InvalidParameterException, WeakPasswordException {
        final String FIRST_NAME = customer.getFirstName();
        final String LAST_NAME = customer.getLastName();
        final String PASSWORD = customer.getPassword();
        final String EMAIL = customer.getEmail();
        final String CONTACT_NUMBER_1 = customer.getContactNumber1();
        final String CONTACT_NUMBER_2 = customer.getContactNumber2();
        //
        // Validate the first name
        //
        if(!validation.validNonNullAndNonBlankParam(FIRST_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, FIRST_NAME));
        }
        //
        // Validate the last name
        //
        if(!validation.validNonNullAndNonBlankParam(LAST_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, LAST_NAME));
        }
        //
        // Make sure the email is not empty and unique
        //
        if(!validation.validEmail(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(CUSTOMER_REPOSITORY.existsCustomerByEmail(customer.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        //
        // Check if contact number 1 is valid
        //
        if(!validation.validContactNumber(CONTACT_NUMBER_1)) {
            throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_1));
        }
        else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber1(customer.getContactNumber1()).isPresent()) {
            throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_1));
        }
        //
        // Check if contact number 2 is valid
        //
        if(!validation.validContactNumber(CONTACT_NUMBER_2)) {
            throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_2));
        }
        else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber2(customer.getContactNumber2()).isPresent()) {
            throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_2));
        }
        //
        // Make sure the password is valid
        //
        if(!validation.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!validation.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        CUSTOMER_REPOSITORY.save(customer);
        return true;
    }

    @Transactional
    public Boolean updateCustomer(
            int customerId,
            String firstName,
            String lastName,
            String contactNumber1,
            String contactNumber2
    ) throws InvalidParameterException {
        final Customer PERSISTED_CUSTOMER = fetchCustomerById(customerId);
        //
        // Check if first name is valid
        //
        if(firstName != null) {
            if(!validation.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            PERSISTED_CUSTOMER.setFirstName(firstName);
        }
        //
        // Check if last name is valid
        //
        if(lastName != null) {
            if(!validation.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, lastName));
            }
            PERSISTED_CUSTOMER.setLastName(lastName);
        }
        //
        // Validate contact number 1
        //
        if(contactNumber1 != null) {
            if(!validation.validContactNumber(contactNumber1)) {
                throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, contactNumber1));
            }
            else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber1(contactNumber1).isPresent() &&
                    !PERSISTED_CUSTOMER.getContactNumber1().equals(contactNumber1)) {
                throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, contactNumber1));
            }
            PERSISTED_CUSTOMER.setContactNumber1(contactNumber1);
        }
        //
        // Validate contact number 2
        //
        if(contactNumber2 != null) {
            if(!validation.validContactNumber(contactNumber2)) {
                throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, contactNumber2));
            }
            else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber2(contactNumber2).isPresent() &&
                    !PERSISTED_CUSTOMER.getContactNumber2().equals(contactNumber2)) {
                throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, contactNumber2));
            }
            PERSISTED_CUSTOMER.setContactNumber2(contactNumber2);
        }
        CUSTOMER_REPOSITORY.save(PERSISTED_CUSTOMER);
        return true;
    }

    @Transactional
    public Boolean updateCredentials(@NotNull UpdateCustomerCredentialsRequest request)
            throws InvalidParameterException, WeakPasswordException {
        final Customer PERSISTED_CUSTOMER = fetchCustomerById(request.getId());
        final String EMAIL = request.getEmail();
        final String PASSWORD = request.getPassword();
        //
        // Validate the email address
        //
        if(EMAIL != null) {
            if(!validation.validEmail(EMAIL)) {
                throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
            }
            else if(CUSTOMER_REPOSITORY.existsCustomerByEmail(EMAIL) && !PERSISTED_CUSTOMER.getEmail().equals(EMAIL)) {
                throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
            }
            PERSISTED_CUSTOMER.setEmail(EMAIL);
        }
        //
        // Validate password
        //
        if(PASSWORD != null) {
            if(!validation.validNonBlankParam(PASSWORD)) {
                throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
            }
            else if(!validation.validPassword(PASSWORD)) {
                throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
            }
            PERSISTED_CUSTOMER.setPassword(PASSWORD);
        }
        CUSTOMER_REPOSITORY.save(PERSISTED_CUSTOMER);
        return true;
    }

    public boolean deleteCustomer(int customerId) {
        boolean exists = CUSTOMER_REPOSITORY.existsById(customerId);
        if(!exists) {
            throw new CustomerNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, customerId));
        }
        CUSTOMER_REPOSITORY.deleteById(customerId);
        return true;
    }

    public void saveCustomer(Customer customer) {
        CUSTOMER_REPOSITORY.save(customer);
    }
}

package com.nirvan.bauhinia.customer;

import com.nirvan.bauhinia.exception.CustomerNotFoundException;
import com.nirvan.bauhinia.exception.InvalidCredentialsException;
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
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Customer with the following id does not exist: %s";
    private static final String CONTACT_NUMBER_NOT_FOUND_MESSAGE = "Customer with the following contact number does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s. Make sure to include " +
            "at least 8 characters. 1 uppercase character, 1 lowercase character and 1 special character";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String EMAIL_NOT_FOUND_MESSAGE = "Customer with the following email does not exist: %s";
    private static final String DUPLICATE_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_CONTACT_NUMBER_MESSAGE = "Contact number is invalid: %s";
    private static final String DUPLICATE_CONTACT_NUMBER_MESSAGE = "Contact number is taken: %s";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";

    public CustomerStats getRegisteredCustomerCount() {
        Integer count = CUSTOMER_REPOSITORY.getRegisteredCustomerCount().get();
        return new CustomerStats(count);
    }

    public List<Customer> fetchAllCustomers() {
        return CUSTOMER_REPOSITORY.findAll();
    }

    public Customer fetchCustomerById(int customerId) throws CustomerNotFoundException {
        return CUSTOMER_REPOSITORY.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, customerId)));
    }

    public Customer fetchCustomerByEmail(String email) throws CustomerNotFoundException {
        if(CUSTOMER_REPOSITORY.findCustomerByEmail(email).isPresent()) {
            return CUSTOMER_REPOSITORY.findCustomerByEmail(email).get();
        }
        return null;
    }

    public Customer fetchCustomerByContactNumber(String contactNumber) {
        if(CUSTOMER_REPOSITORY.findCustomerByContactNumber(contactNumber).isPresent()) {
            return CUSTOMER_REPOSITORY.findCustomerByContactNumber(contactNumber).get();
        }
        return null;
    }

    public Customer addCustomer(@NotNull Customer customer) throws InvalidParameterException, WeakPasswordException {
        final String FIRST_NAME = customer.getFirstName();
        final String LAST_NAME = customer.getLastName();
        final String PASSWORD = customer.getPassword();
        final String EMAIL = customer.getEmail();
        final String CONTACT_NUMBER_1 = customer.getContactNumber1();
        final String CONTACT_NUMBER_2 = customer.getContactNumber2();
        if(!VALIDATION.validNonNullAndNonBlankParam(FIRST_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, FIRST_NAME));
        }

        if(!VALIDATION.validNonNullAndNonBlankParam(LAST_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, LAST_NAME));
        }

        if(!VALIDATION.validEmail(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(CUSTOMER_REPOSITORY.existsCustomerByEmail(customer.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }

        if(!VALIDATION.validContactNumber(CONTACT_NUMBER_1)) {
            throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_1));
        }
        else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber1(customer.getContactNumber1()).isPresent()) {
            throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_1));
        }

        if(!VALIDATION.validContactNumber(CONTACT_NUMBER_2)) {
            throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_2));
        }
        else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber2(customer.getContactNumber2()).isPresent()) {
            throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, CONTACT_NUMBER_2));
        }

        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        CUSTOMER_REPOSITORY.save(customer);
        return customer;
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

        if(firstName != null) {
            if(!VALIDATION.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            PERSISTED_CUSTOMER.setFirstName(firstName);
        }

        if(lastName != null) {
            if(!VALIDATION.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, lastName));
            }
            PERSISTED_CUSTOMER.setLastName(lastName);
        }

        if(contactNumber1 != null) {
            if(!VALIDATION.validContactNumber(contactNumber1)) {
                throw new InvalidParameterException(String.format(INVALID_CONTACT_NUMBER_MESSAGE, contactNumber1));
            }
            else if(CUSTOMER_REPOSITORY.findCustomerByContactNumber1(contactNumber1).isPresent() &&
                    !PERSISTED_CUSTOMER.getContactNumber1().equals(contactNumber1)) {
                throw new InvalidParameterException(String.format(DUPLICATE_CONTACT_NUMBER_MESSAGE, contactNumber1));
            }
            PERSISTED_CUSTOMER.setContactNumber1(contactNumber1);
        }

        if(contactNumber2 != null) {
            if(!VALIDATION.validContactNumber(contactNumber2)) {
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

        if(EMAIL != null) {
            if(!VALIDATION.validEmail(EMAIL)) {
                throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
            }
            else if(CUSTOMER_REPOSITORY.existsCustomerByEmail(EMAIL) && !PERSISTED_CUSTOMER.getEmail().equals(EMAIL)) {
                throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
            }
            PERSISTED_CUSTOMER.setEmail(EMAIL);
        }

        if(PASSWORD != null) {
            if(!VALIDATION.validNonBlankParam(PASSWORD)) {
                throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
            }
            else if(!VALIDATION.validPassword(PASSWORD)) {
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

    public Customer login(CustomerLoginRequest loginRequest) {
        final String EMAIL = loginRequest.getEmail();
        final String PASSWORD = loginRequest.getPassword();

        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }

        Customer customer = fetchCustomerByEmail(EMAIL);
        if (customer == null) {
            throw new CustomerNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, EMAIL));
        }
        if(!PASSWORD.equals(customer.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }
        return customer;
    }

}

package com.nirvan.bauhinia.employee.inventoryclerk;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.employee.EmployeeService;
import com.nirvan.bauhinia.employee.UpdateEmployeeCredentialsRequest;
import com.nirvan.bauhinia.exception.*;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class InventoryClerkServiceV2 extends EmployeeService {
    private final InventoryClerkRepository IC_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Inventory clerk with the following id does not exist: %s";
    private static final String EMAIL_NOT_FOUND_MESSAGE = "Inventory clerk with the following email does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String DUPLICATE_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";

    @Autowired
    public InventoryClerkServiceV2(Validation validation, InventoryClerkRepository icRepository) {
        super(validation);
        IC_REPOSITORY = icRepository;
        this.VALIDATION = validation;
    }

    public InventoryClerkStats getInventoryClerkCount() {
        Integer count = IC_REPOSITORY.getEmployeeCount().get();
        return new InventoryClerkStats(count);
    }

    public InventoryClerk fetchInventoryClerkById(int employeeId) {
        return IC_REPOSITORY.findById(employeeId)
                .orElseThrow(() -> new InventoryClerkNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId)));
    }

    private InventoryClerk fetchInventoryClerkByEmail(String email) {
        return IC_REPOSITORY.findInventoryClerkByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, email)));
    }

    public List<InventoryClerk> fetchAllInventoryClerks() {
        return new ArrayList<>(IC_REPOSITORY.findAll());
    }

    public Boolean addInventoryClerk(@NotNull InventoryClerk inventoryClerk)
            throws InvalidParameterException, WeakPasswordException {
        final String EMAIL = inventoryClerk.getEmail();
        priorInsertValidation(inventoryClerk);

        if(IC_REPOSITORY.existsInventoryClerkByEmail(inventoryClerk.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }

        inventoryClerk.setAccountType(AccountType.ADMINISTRATOR);
        try {
            IC_REPOSITORY.save(inventoryClerk);
        }
        catch (DataIntegrityViolationException ex) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        return true;
    }

    @Transactional
    public Boolean updateInventoryClerk(
            int employeeId,
            String firstName,
            String lastName
    ) {
        final InventoryClerk PERSISTED_INVENTORY_CLERK = fetchInventoryClerkById(employeeId);

        if(firstName != null) {
            if(!VALIDATION.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            PERSISTED_INVENTORY_CLERK.setFirstName(firstName);
        }

        if(lastName != null) {
            if(!VALIDATION.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, lastName));
            }
            PERSISTED_INVENTORY_CLERK.setLastName(lastName);
        }
        IC_REPOSITORY.save(PERSISTED_INVENTORY_CLERK);
        return true;
    }

    @Transactional
    public Boolean updateCredentials(UpdateEmployeeCredentialsRequest updateCredentialsRequest)
            throws InvalidParameterException, WeakPasswordException {
        final InventoryClerk PERSISTED_INVENTORY_CLERK = fetchInventoryClerkById(updateCredentialsRequest.getId());
        final String EMAIL = updateCredentialsRequest.getEmail();
        final String PASSWORD = updateCredentialsRequest.getPassword();

        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(IC_REPOSITORY.existsInventoryClerkByEmail(EMAIL) && !EMAIL.equals(PERSISTED_INVENTORY_CLERK.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        PERSISTED_INVENTORY_CLERK.setEmail(EMAIL);

        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        PERSISTED_INVENTORY_CLERK.setPassword(PASSWORD);
        IC_REPOSITORY.save(PERSISTED_INVENTORY_CLERK);
        return true;
    }

    public Boolean deleteInventoryClerk(int employeeId) {
        if(!IC_REPOSITORY.existsById(employeeId)) {
            throw new InventoryClerkNotFoundException("Inventory clerk with id (" + employeeId + ") does not exist");
        }
        IC_REPOSITORY.deleteById(employeeId);
        return true;
    }

    public InventoryClerk login(EmployeeLoginRequest loginRequest) {
        final String EMAIL = loginRequest.getEmail();
        final String PASSWORD = loginRequest.getPassword();

        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }

        InventoryClerk inventoryClerk = fetchInventoryClerkByEmail(EMAIL);
        if(!PASSWORD.equals(inventoryClerk.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }
        return inventoryClerk;
    }

}

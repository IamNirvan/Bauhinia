package com.nirvan.bauhinia.employee.chiefaccountant;

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
public class ChiefAccountantServiceV2 extends EmployeeService {
    private final ChiefAccountantRepository CA_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Administrator with the following id does not exist: %s";
    private static final String EMAIL_NOT_FOUND_MESSAGE = "Chief accountant with the following email does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String DUPLICATE_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";

    @Autowired
    public ChiefAccountantServiceV2(Validation VALIDATION, ChiefAccountantRepository caRepository, Validation validation) {
        super(VALIDATION);
        CA_REPOSITORY = caRepository;
        this.VALIDATION = validation;
    }

    public ChiefAccountantStats getChiefAccountantCount() {
        Integer count = CA_REPOSITORY.getEmployeeCount().get();
        return new ChiefAccountantStats(count);
    }

    private ChiefAccountant fetchAdministratorByEmail(String email) {
        return CA_REPOSITORY.findChiefAccountantByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, email)));
    }

    public ChiefAccountant fetchChiefAccountantById(int employeeId) throws ChiefAccountantNotFoundException {
        return CA_REPOSITORY.findById(employeeId)
                .orElseThrow(() -> new ChiefAccountantNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId)));
    }

    public List<ChiefAccountant> fetchAllChiefAccountants() {
        return new ArrayList<>(CA_REPOSITORY.findAll());
    }

    public Boolean addChiefAccountant(@NotNull ChiefAccountant chiefAccountant)
            throws InvalidParameterException, WeakPasswordException  {
        final String EMAIL = chiefAccountant.getEmail();
        priorInsertValidation(chiefAccountant);
        //
        // Check if the administrator's email is not taken
        //
        if(CA_REPOSITORY.existsChiefAccountantByEmail(chiefAccountant.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        //
        // Set the account type
        //
        chiefAccountant.setAccountType(AccountType.CHIEF_ACCOUNTANT);
        try {
            CA_REPOSITORY.save(chiefAccountant);
        }
        catch (DataIntegrityViolationException ex) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        return true;
    }

    @Transactional
    public Boolean update(
            int employeeId,
            String firstName,
            String lastName
    ) throws InvalidParameterException {
        final ChiefAccountant PERSISTED_CHIEF_ACCOUNTANT = fetchChiefAccountantById(employeeId);
        //
        // Check if first name is valid
        //
        if(firstName != null) {
            if(!VALIDATION.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            PERSISTED_CHIEF_ACCOUNTANT.setFirstName(firstName);
        }
        //
        // Check if last name is valid
        //
        if(lastName != null) {
            if(!VALIDATION.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, lastName));
            }
            PERSISTED_CHIEF_ACCOUNTANT.setLastName(lastName);
        }
        CA_REPOSITORY.save(PERSISTED_CHIEF_ACCOUNTANT);
        return true;
    }

    public boolean updateCredentials(@NotNull UpdateEmployeeCredentialsRequest updateCredentialsRequest)
            throws InvalidParameterException, WeakPasswordException {
        final ChiefAccountant PERSISTED_CHIEF_ACCOUNTANT = fetchChiefAccountantById(updateCredentialsRequest.getId());
        final String EMAIL = updateCredentialsRequest.getEmail();
        final String PASSWORD = updateCredentialsRequest.getPassword();
        //
        // Validate email
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(CA_REPOSITORY.existsChiefAccountantByEmail(EMAIL) && !EMAIL.equals(PERSISTED_CHIEF_ACCOUNTANT.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        PERSISTED_CHIEF_ACCOUNTANT.setEmail(EMAIL);
        //
        // Validate password
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        PERSISTED_CHIEF_ACCOUNTANT.setPassword(PASSWORD);
        CA_REPOSITORY.save(PERSISTED_CHIEF_ACCOUNTANT);
        return true;
    }

    public Boolean deleteChiefAccountant(int employeeId) throws ChiefAccountantNotFoundException {
        if(!CA_REPOSITORY.existsById(employeeId)) {
            throw new ChiefAccountantNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId));
        }
        CA_REPOSITORY.deleteById(employeeId);
        return true;
    }

    // TODO: implement this...
    public ChiefAccountant login(EmployeeLoginRequest loginRequest) {
        // Check and see if valid (non-null) credentials were passed
        final String EMAIL = loginRequest.getEmail();
        final String PASSWORD = loginRequest.getPassword();

        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }

        // Check if the credentials exist
        ChiefAccountant chiefAccountant = fetchAdministratorByEmail(EMAIL);
        if(!PASSWORD.equals(chiefAccountant.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }
        return chiefAccountant;
    }
}

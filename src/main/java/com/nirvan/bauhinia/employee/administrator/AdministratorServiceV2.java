package com.nirvan.bauhinia.employee.administrator;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.employee.EmployeeService;
import com.nirvan.bauhinia.employee.UpdateEmployeeCredentialsRequest;
import com.nirvan.bauhinia.exception.AdminNotFoundException;
import com.nirvan.bauhinia.exception.InvalidCredentialsException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.WeakPasswordException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AdministratorServiceV2 extends EmployeeService {
    private final AdministratorRepository ADMIN_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Administrator with the following id does not exist: %s";
    private static final String EMAIL_NOT_FOUND_MESSAGE = "Administrator with the following email does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String DUPLICATE_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s";
    private static final String INVALID_CREDENTIALS_MESSAGE = "Invalid credentials";

    @Autowired
    public AdministratorServiceV2(Validation VALIDATION, AdministratorRepository adminRepository, Validation validation) {
        super(VALIDATION);
        ADMIN_REPOSITORY = adminRepository;
        this.VALIDATION = validation;
    }

    public Administrator fetchAdminById(int employeeId) {
        return  ADMIN_REPOSITORY.findById(employeeId)
                .orElseThrow(() -> new AdminNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId)));
    }

    private Administrator fetchAdministratorByEmail(String email) {
        return ADMIN_REPOSITORY.findAdministratorByEmail(email)
                .orElseThrow(() -> new AdminNotFoundException(String.format(EMAIL_NOT_FOUND_MESSAGE, email)));
    }

    public boolean addAdministrator(@NotNull Administrator administrator)
            throws InvalidParameterException, WeakPasswordException {
        final String EMAIL = administrator.getEmail();
        priorInsertValidation(administrator);
        //
        // Check if the administrator's email is not taken
        //
        if(ADMIN_REPOSITORY.existsAdministratorByEmail(administrator.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        //
        // Set the account type
        //
        administrator.setAccountType(AccountType.ADMINISTRATOR);

        try {
            ADMIN_REPOSITORY.save(administrator);
        }
        catch (DataIntegrityViolationException ex) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        return true;
    }

    @Transactional
    public boolean updateBasicDetails(
            int employeeId,
            String firstName,
            String lastName
    ) throws InvalidParameterException {
        final Administrator PERSISTED_ADMIN = fetchAdminById(employeeId);
        //
        // Check if first name is valid
        //
        if(firstName != null) {
            if(!VALIDATION.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            PERSISTED_ADMIN.setFirstName(firstName);
        }
        //
        // Check if last name is valid
        //
        if(lastName != null) {
            if(!VALIDATION.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, lastName));
            }
            PERSISTED_ADMIN.setLastName(lastName);
        }
        ADMIN_REPOSITORY.save(PERSISTED_ADMIN);
        return true;
    }

    public boolean updateCredentials(@NotNull UpdateEmployeeCredentialsRequest updateCredentialsRequest)
            throws InvalidParameterException, WeakPasswordException {
        final Administrator PERSISTED_ADMIN = fetchAdminById(updateCredentialsRequest.getId());
        final String EMAIL = updateCredentialsRequest.getEmail();
        final String PASSWORD = updateCredentialsRequest.getPassword();
        //
        // Validate email
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(ADMIN_REPOSITORY.existsAdministratorByEmail(EMAIL) && !EMAIL.equals(PERSISTED_ADMIN.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        PERSISTED_ADMIN.setEmail(EMAIL);
        //
        // Validate password
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        PERSISTED_ADMIN.setPassword(PASSWORD);
        ADMIN_REPOSITORY.save(PERSISTED_ADMIN);
        return false;
    }

    public boolean deleteAdministrator(@NotNull int employeeId) {
        boolean exists = ADMIN_REPOSITORY.existsById(employeeId);
        if(!exists) {
            throw new AdminNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId));
        }
        ADMIN_REPOSITORY.deleteById(employeeId);
        return true;
    }

    public Administrator login(EmployeeLoginRequest loginRequest) {
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
        Administrator administrator = fetchAdministratorByEmail(EMAIL);
        if(!PASSWORD.equals(administrator.getPassword())) {
            throw new InvalidCredentialsException(INVALID_CREDENTIALS_MESSAGE);
        }
        return administrator;
    }
}

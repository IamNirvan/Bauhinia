package com.nirvan.bauhinia.employee.chiefaccountant;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.exception.ChiefAccountantNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.WeakPasswordException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChiefAccountantService {
    private final ChiefAccountantRepository CA_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Chief accountant with the following id does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String DUPLICATED_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s";

    public ChiefAccountant fetchChiefAccountantById(int employeeId) throws ChiefAccountantNotFoundException {
        return CA_REPOSITORY.findById(employeeId)
                .orElseThrow(() -> new ChiefAccountantNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId)));
    }

    public List<ChiefAccountant> fetchAllChiefAccountants() {
        return new ArrayList<>(CA_REPOSITORY.findAll());
    }

    public ChiefAccountant addChiefAccountant(@NotNull ChiefAccountant chiefAccountant)
            throws InvalidParameterException, WeakPasswordException  {
        final String FIRST_NAME = chiefAccountant.getFirstName();
        final String LAST_NAME = chiefAccountant.getLastName();
        final String EMAIL = chiefAccountant.getEmail();
        final String PASSWORD = chiefAccountant.getPassword();
        //
        // Check if first name is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(FIRST_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, FIRST_NAME));
        }
        //
        // Check if last name is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(LAST_NAME)) {
            throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, LAST_NAME));
        }
        //
        // Check if email is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(!VALIDATION.validEmail(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(CA_REPOSITORY.existsChiefAccountantByEmail(EMAIL)) {
            throw new InvalidParameterException(String.format(DUPLICATED_EMAIL_MESSAGE, EMAIL));
        }
        //
        // Check if password is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        chiefAccountant.setAccountType(AccountType.CHIEF_ACCOUNTANT);
        CA_REPOSITORY.save(chiefAccountant);
        return chiefAccountant;
    }

    @Transactional
    public Boolean update(
            int employeeId,
            String firstName,
            String lastName
    ) throws InvalidParameterException {
        final ChiefAccountant persistedChiefAccountant = fetchChiefAccountantById(employeeId);
        //
        // Check if first name is valid
        //
        if(firstName != null) {
            if(!VALIDATION.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            persistedChiefAccountant.setFirstName(firstName);
        }
        //
        // Check if last name is valid
        //
        if(lastName != null) {
            if(!VALIDATION.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, lastName));
            }
            persistedChiefAccountant.setLastName(lastName);
        }
        CA_REPOSITORY.save(persistedChiefAccountant);
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
    public String login(EmployeeLoginRequest loginRequest) {
        return "Method must be implemented";
    }
}

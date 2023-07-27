package com.nirvan.bauhinia.employee;

import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.WeakPasswordException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final Validation VALIDATION;
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s";

    /**
     * Validates the attributes of an employee instance prior to persisting it in the database.
     * All validations are performed except for an email validation which checks if the email is taken
     * @param employee an instance of Employee to be persisted
     * */
    public Boolean priorInsertValidation(@NotNull Employee employee) throws InvalidParameterException, WeakPasswordException {
        final String FIRST_NAME = employee.getFirstName();
        final String LAST_NAME = employee.getLastName();
        final String EMAIL = employee.getEmail();
        final String PASSWORD = employee.getPassword();
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
        if(!VALIDATION.validEmail(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        //
        // Check if the password is valid
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }

        return true;
    }
}

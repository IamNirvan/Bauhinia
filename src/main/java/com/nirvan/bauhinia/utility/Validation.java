package com.nirvan.bauhinia.utility;

import com.nirvan.bauhinia.employee.EmployeeRepository;
import com.nirvan.bauhinia.exception.WeakPasswordException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {
    private static Validation instance;
    private Pattern pattern;
    private Matcher matcher;
    private static final String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$";
    private static final String contactNumberPattern = "07[0-9]{8}";
    private static final String emailPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static Validation getInstance() {
        if(instance == null) {
            return new Validation();
        }
        return instance;
    }

    /**
     * @param password password that needs to be tested
     * @return true if the password is valid and false if not
     * */
    public boolean validPassword(String password) {
        pattern = Pattern.compile(passwordPattern);
        matcher = pattern.matcher(password);
        return  matcher.matches();
    }

    /**
     * @param contactNumber the contact number that needs to be tested
     * @return true if the contact number is valid and false if not
     * */
    public boolean validContactNumber(String contactNumber) {
        pattern = Pattern.compile(contactNumberPattern);
        matcher = pattern.matcher(contactNumber);
        return matcher.matches();
    }

    /**
     * @param email the email that needs to be tested
     * @return true if the email is valid and false if not
     * */
    public boolean validEmail(String email) {
        pattern = Pattern.compile(emailPattern);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * @param param a string that needs to be tested
     * @return true if the string is not null and not empty. Returns false if not
     * */
    public boolean validNonNullAndNonBlankParam(String param) {
        return param != null && !param.isBlank();
    }

    /**
     * @param param a string that needs to be tested
     * @return true if the string is not empty. Returns false if not
     * */
    public boolean validNonBlankParam(String param) {
        return !param.isBlank();
    }

}

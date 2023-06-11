package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.employee.EmployeeLoginRequest;
import com.nirvan.bauhinia.employee.EmployeeService;
import com.nirvan.bauhinia.employee.UpdateEmployeeCredentialsRequest;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ProductionManagerNotFoundException;
import com.nirvan.bauhinia.exception.WeakPasswordException;
import com.nirvan.bauhinia.utility.Validation;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductionManagerService extends EmployeeService {
    private final ProductionManagerRepository PM_REPOSITORY;
    private final Validation VALIDATION;
    private static final String ID_NOT_FOUND_MESSAGE = "Production manager with the following id does not exist: %s";
    private static final String INVALID_FIRST_NAME_MESSAGE = "First name is invalid: %s";
    private static final String INVALID_LAST_NAME_MESSAGE = "Last name is invalid: %s";
    private static final String INVALID_EMAIL_MESSAGE = "Email is invalid: %s";
    private static final String DUPLICATE_EMAIL_MESSAGE = "Email is taken: %s";
    private static final String INVALID_PASSWORD_MESSAGE = "Password is invalid: %s";
    private static final String WEAK_PASSWORD_MESSAGE = "Password is weak: %s";

    @Autowired
    public ProductionManagerService(Validation validation, ProductionManagerRepository pmRepository) {
        super(validation);
        PM_REPOSITORY = pmRepository;
        this.VALIDATION = validation;
    }

    public ProductionManager fetchProductionManagerById(int employeeId) {
        return PM_REPOSITORY.findById(employeeId)
                .orElseThrow(() -> new ProductionManagerNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId)));
    }

    public List<ProductionManager> fetchAllProductionManagers() {
        return new ArrayList<>(PM_REPOSITORY.findAll());
    }

    public Boolean addProductionManager(@NotNull ProductionManager productionManager)
            throws InvalidParameterException, WeakPasswordException {
        final String EMAIL = productionManager.getEmail();
        priorInsertValidation(productionManager);
        //
        // Check if the production manager's email is not taken
        //
        if(PM_REPOSITORY.existsProductionManagerByEmail(productionManager.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        //
        // Set the account type
        //
        productionManager.setAccountType(AccountType.PRODUCTION_MANAGER);
        PM_REPOSITORY.save(productionManager);
        return true;
    }

    @Transactional
    public Boolean updateProductionManager(
            int employeeId,
            String firstName,
            String lastName
    ) throws InvalidParameterException {
        final ProductionManager PERSISTED_PRODUCTION_MANAGER = fetchProductionManagerById(employeeId);
        //
        // Check if first name is valid
        //
        if(firstName != null) {
            if(!VALIDATION.validNonBlankParam(firstName)) {
                throw new InvalidParameterException(String.format(INVALID_FIRST_NAME_MESSAGE, firstName));
            }
            PERSISTED_PRODUCTION_MANAGER.setFirstName(firstName);
        }
        //
        // Check if last name is valid
        //
        if(lastName != null) {
            if(!VALIDATION.validNonBlankParam(lastName)) {
                throw new InvalidParameterException(String.format(INVALID_LAST_NAME_MESSAGE, lastName));
            }
            PERSISTED_PRODUCTION_MANAGER.setLastName(lastName);
        }
        PM_REPOSITORY.save(PERSISTED_PRODUCTION_MANAGER);
        return true;
    }

    public boolean updateCredentials(@NotNull UpdateEmployeeCredentialsRequest updateCredentialsRequest)
            throws InvalidParameterException, WeakPasswordException {
        final ProductionManager PERSISTED_PRODUCTION_MANAGER = fetchProductionManagerById(updateCredentialsRequest.getId());
        final String EMAIL = updateCredentialsRequest.getEmail();
        final String PASSWORD = updateCredentialsRequest.getPassword();
        //
        // Validate email
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(EMAIL)) {
            throw new InvalidParameterException(String.format(INVALID_EMAIL_MESSAGE, EMAIL));
        }
        else if(PM_REPOSITORY.existsProductionManagerByEmail(EMAIL) && !EMAIL.equals(PERSISTED_PRODUCTION_MANAGER.getEmail())) {
            throw new InvalidParameterException(String.format(DUPLICATE_EMAIL_MESSAGE, EMAIL));
        }
        PERSISTED_PRODUCTION_MANAGER.setEmail(EMAIL);
        //
        // Validate password
        //
        if(!VALIDATION.validNonNullAndNonBlankParam(PASSWORD)) {
            throw new InvalidParameterException(String.format(INVALID_PASSWORD_MESSAGE, PASSWORD));
        }
        else if(!VALIDATION.validPassword(PASSWORD)) {
            throw new WeakPasswordException(String.format(WEAK_PASSWORD_MESSAGE, PASSWORD));
        }
        PERSISTED_PRODUCTION_MANAGER.setPassword(PASSWORD);
        PM_REPOSITORY.save(PERSISTED_PRODUCTION_MANAGER);
        return true;
    }

    public Boolean deleteProductionManager(int employeeId) {
        if(!PM_REPOSITORY.existsById(employeeId)) {
            throw new ProductionManagerNotFoundException(String.format(ID_NOT_FOUND_MESSAGE, employeeId));
        }
        PM_REPOSITORY.deleteById(employeeId);
        return true;
    }

    // TODO: implement this...
    public String login(EmployeeLoginRequest loginRequest) {
        return "Method must be implemented";
    }
}

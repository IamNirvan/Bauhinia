package com.nirvan.bauhinia.employee.productionmanager;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.ProductionManagerNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductionManagerService {

    private final ProductionManagerRepository PM_REPOSITORY;

    @Autowired
    public ProductionManagerService(ProductionManagerRepository pmRepository) {
        PM_REPOSITORY = pmRepository;
    }


    private ProductionManager getAdmin(int id) {
        return PM_REPOSITORY.findById(id)
                .orElseThrow(() -> new ProductionManagerNotFoundException("Production manager with id (" + id + ") does not exist"));
    }

    public List<ProductionManager> select() {
        return new ArrayList<>(PM_REPOSITORY.findAll());
    }

    public ProductionManager selectById(int id) {
        return getAdmin(id);
    }

    public ProductionManager insert(ProductionManager productionManager) {
        // Check if first name is valid
        if(productionManager.getFirstName().length() == 0) {
            throw new InvalidParameterException("First name (" + productionManager.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(productionManager.getLastName().length() == 0) {
            throw new InvalidParameterException("Last name (" + productionManager.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(productionManager.getEmail().length() == 0) {
            throw new InvalidParameterException("Email (" + productionManager.getEmail() + ") is invalid");
        }
        else if(PM_REPOSITORY.existsProductionManagerByEmail(productionManager.getEmail())) {
            throw new InvalidParameterException("Email (" + productionManager.getEmail() + ") is taken");
        }

        // Set the account type
        productionManager.setAccountType(AccountType.PRODUCTION_MANAGER);

        PM_REPOSITORY.save(productionManager);
        return productionManager;
    }

    @Transactional
    public ProductionManager update(ProductionManager updatedProductionManager) {
        ProductionManager productionManager = getAdmin(updatedProductionManager.getId());

        // Check if first name is valid
        if(updatedProductionManager.getFirstName().length() != 0) {
            if(!Objects.equals(productionManager.getFirstName(), updatedProductionManager.getFirstName())) {
                productionManager.setFirstName(updatedProductionManager.getFirstName());
            }
        }
        else {
            throw new InvalidParameterException("First name (" + updatedProductionManager.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(updatedProductionManager.getLastName().length() != 0) {
            if(!Objects.equals(productionManager.getLastName(), updatedProductionManager.getLastName())) {
                productionManager.setLastName(updatedProductionManager.getLastName());
            }
        }
        else {
            throw new InvalidParameterException("Last name (" + updatedProductionManager.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(updatedProductionManager.getEmail().length() != 0) {
            if(!Objects.equals(productionManager.getEmail(), updatedProductionManager.getEmail())) {
                if(!PM_REPOSITORY.existsProductionManagerByEmail(updatedProductionManager.getEmail())) {
                    productionManager.setEmail(updatedProductionManager.getEmail());
                }
                else {
                    throw new InvalidParameterException("Email (" + updatedProductionManager.getEmail() + ") is taken");
                }
            }
        }
        else {
            throw new InvalidParameterException("Email (" + updatedProductionManager.getEmail() + ") is invalid");
        }

        return productionManager;
    }

    public void delete(int employeeId) {
        if(!PM_REPOSITORY.existsById(employeeId)) {
            throw new ProductionManagerNotFoundException("Production manager with id (" + employeeId + ") does not exist");
        }
        PM_REPOSITORY.deleteById(employeeId);
    }
}

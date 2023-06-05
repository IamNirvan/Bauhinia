package com.nirvan.bauhinia.employee.inventoryclerk;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import com.nirvan.bauhinia.exception.InventoryClerkNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryClerkService {

    private final InventoryClerkRepository IC_REPOSITORY;

    @Autowired
    public InventoryClerkService(InventoryClerkRepository inventoryClerkRepository) {
        IC_REPOSITORY = inventoryClerkRepository;
    }

    private InventoryClerk getInventoryClerk(int id) {
        return IC_REPOSITORY.findById(id)
                .orElseThrow(() -> new InventoryClerkNotFoundException("Inventory clerk with id (" + id + ") does not exist"));
    }

    public List<InventoryClerk> select() {
        return new ArrayList<>(IC_REPOSITORY.findAll());
    }

    public InventoryClerk selectById(int id) {
        return getInventoryClerk(id);
    }

    public InventoryClerk insert(InventoryClerk inventoryClerk) {
        // Check if first name is valid
        if(inventoryClerk.getFirstName().length() == 0) {
            throw new InvalidParameterException("First name (" + inventoryClerk.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(inventoryClerk.getLastName().length() == 0) {
            throw new InvalidParameterException("Last name (" + inventoryClerk.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(inventoryClerk.getEmail().length() == 0) {
            throw new InvalidParameterException("Email (" + inventoryClerk.getEmail() + ") is invalid");
        }
        else if(IC_REPOSITORY.existsInventoryClerkByEmail(inventoryClerk.getEmail())) {
            throw new InvalidParameterException("Email (" + inventoryClerk.getEmail() + ") is taken");
        }

        // Set the account type
        inventoryClerk.setAccountType(AccountType.INVENTORY_CLERK);

        IC_REPOSITORY.save(inventoryClerk);
        return inventoryClerk;
    }

    @Transactional
    public InventoryClerk update(InventoryClerk updatedInventoryClerk) {
        InventoryClerk persistedInventoryClerk = getInventoryClerk(updatedInventoryClerk.getId());

        // Check if first name is valid
        if(updatedInventoryClerk.getFirstName().length() != 0) {
            if(!Objects.equals(persistedInventoryClerk.getFirstName(), updatedInventoryClerk.getFirstName())) {
                persistedInventoryClerk.setFirstName(updatedInventoryClerk.getFirstName());
            }
        }
        else {
            throw new InvalidParameterException("First name (" + updatedInventoryClerk.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(updatedInventoryClerk.getLastName().length() != 0) {
            if(!Objects.equals(persistedInventoryClerk.getLastName(), updatedInventoryClerk.getLastName())) {
                persistedInventoryClerk.setLastName(updatedInventoryClerk.getLastName());
            }
        }
        else {
            throw new InvalidParameterException("Last name (" + updatedInventoryClerk.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(updatedInventoryClerk.getEmail().length() != 0) {
            if(!Objects.equals(persistedInventoryClerk.getEmail(), updatedInventoryClerk.getEmail())) {
                if(!IC_REPOSITORY.existsInventoryClerkByEmail(updatedInventoryClerk.getEmail())) {
                    persistedInventoryClerk.setEmail(updatedInventoryClerk.getEmail());
                }
                else {
                    throw new InvalidParameterException("Email (" + updatedInventoryClerk.getEmail() + ") is taken");
                }
            }
        }
        else {
            throw new InvalidParameterException("Email (" + updatedInventoryClerk.getEmail() + ") is invalid");
        }

        return persistedInventoryClerk;
    }

    public void delete(int employeeId) {
        if(!IC_REPOSITORY.existsById(employeeId)) {
            throw new InventoryClerkNotFoundException("Inventory clerk with id (" + employeeId + ") does not exist");
        }
        IC_REPOSITORY.deleteById(employeeId);
    }
}

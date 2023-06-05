package com.nirvan.bauhinia.employee.administrator;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.exception.AdminNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AdministratorService {

    private final AdministratorRepository ADMIN_REPOSITORY;

    @Autowired
    public AdministratorService(AdministratorRepository adminRepository) {
        ADMIN_REPOSITORY = adminRepository;
    }

    private Administrator getAdmin(int id) {
        return  ADMIN_REPOSITORY.findById(id)
                .orElseThrow(() -> new AdminNotFoundException("Administrator with id (" + id + ") does not exist"));
    }

    public List<Administrator> select() {
        return new ArrayList<>(ADMIN_REPOSITORY.findAll());
    }

    public Administrator selectById(int id) {
        return getAdmin(id);
    }

    public Administrator insert(Administrator admin) {
        // Check if first name is valid
        if(admin.getFirstName().length() == 0) {
            throw new InvalidParameterException("First name (" + admin.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(admin.getLastName().length() == 0) {
            throw new InvalidParameterException("Last name (" + admin.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(admin.getEmail().length() == 0) {
            throw new InvalidParameterException("Email (" + admin.getEmail() + ") is invalid");
        }
        else if(ADMIN_REPOSITORY.existsAdministratorByEmail(admin.getEmail())) {
            throw new InvalidParameterException("Email (" + admin.getEmail() + ") is taken");
        }

        // Set the account type
        admin.setAccountType(AccountType.ADMINISTRATOR);
        ADMIN_REPOSITORY.save(admin);
        return admin;
    }

    @Transactional
    public Administrator update(Administrator updatedAdmin) {
        Administrator persistedAdmin = getAdmin(updatedAdmin.getId());

        // Check if first name is valid
        if(updatedAdmin.getFirstName().length() != 0) {
            if(!Objects.equals(persistedAdmin.getFirstName(), updatedAdmin.getFirstName())) {
                persistedAdmin.setFirstName(updatedAdmin.getFirstName());
            }
        }
        else {
            throw new InvalidParameterException("First name (" + updatedAdmin.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(updatedAdmin.getLastName().length() != 0) {
            if(!Objects.equals(persistedAdmin.getLastName(), updatedAdmin.getLastName())) {
                persistedAdmin.setLastName(updatedAdmin.getLastName());
            }
        }
        else {
            throw new InvalidParameterException("Last name (" + updatedAdmin.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(updatedAdmin.getEmail().length() != 0) {
            if(!Objects.equals(persistedAdmin.getEmail(), updatedAdmin.getEmail())) {
                if(!ADMIN_REPOSITORY.existsAdministratorByEmail(updatedAdmin.getEmail())) {
                    persistedAdmin.setEmail(updatedAdmin.getEmail());
                }
                else {
                    throw new InvalidParameterException("Email (" + updatedAdmin.getEmail() + ") is taken");
                }
            }
        }
        else {
            throw new InvalidParameterException("Email (" + updatedAdmin.getEmail() + ") is invalid");
        }

        ADMIN_REPOSITORY.save(persistedAdmin);
        return persistedAdmin;
    }

    public void delete(int employeeId) {
        if(!ADMIN_REPOSITORY.existsById(employeeId)) {
            throw new AdminNotFoundException("Administrator with id (" + employeeId + ") does not exist");
        }
        ADMIN_REPOSITORY.deleteById(employeeId);
    }

}

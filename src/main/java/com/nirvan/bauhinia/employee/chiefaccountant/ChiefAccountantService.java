package com.nirvan.bauhinia.employee.chiefaccountant;

import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.exception.ChiefAccountantNotFoundException;
import com.nirvan.bauhinia.exception.InvalidParameterException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ChiefAccountantService {

    private final ChiefAccountantRepository CA_REPOSITORY;

    @Autowired
    public ChiefAccountantService(ChiefAccountantRepository chiefAccountantRepository) {
        CA_REPOSITORY = chiefAccountantRepository;
    }

    private ChiefAccountant getChiefAccountant(int id) {
        return CA_REPOSITORY.findById(id)
                .orElseThrow(() -> new ChiefAccountantNotFoundException("Chief accountant with id (" + id + ") does not exist"));
    }

    public List<ChiefAccountant> select() {
        return new ArrayList<>(CA_REPOSITORY.findAll());
    }

    public ChiefAccountant selectById(int id) {
        return getChiefAccountant(id);
    }

    public ChiefAccountant insert(ChiefAccountant chiefAccountant) {
        // Check if first name is valid
        if(chiefAccountant.getFirstName().length() == 0) {
            throw new InvalidParameterException("First name (" + chiefAccountant.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(chiefAccountant.getLastName().length() == 0) {
            throw new InvalidParameterException("Last name (" + chiefAccountant.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(chiefAccountant.getEmail().length() == 0) {
            throw new InvalidParameterException("Email (" + chiefAccountant.getEmail() + ") is invalid");
        }
        else if(CA_REPOSITORY.existsChiefAccountantByEmail(chiefAccountant.getEmail())) {
            throw new InvalidParameterException("Email (" + chiefAccountant.getEmail() + ") is taken");
        }

        // Set the account type
        chiefAccountant.setAccountType(AccountType.CHIEF_ACCOUNTANT);

        CA_REPOSITORY.save(chiefAccountant);
        return chiefAccountant;
    }

    @Transactional
    public ChiefAccountant update(ChiefAccountant updatedChiefAccountant) {
        ChiefAccountant persistedChiefAccountant = getChiefAccountant(updatedChiefAccountant.getId());

        // Check if first name is valid
        if(updatedChiefAccountant.getFirstName().length() != 0) {
            if(!Objects.equals(persistedChiefAccountant.getFirstName(), updatedChiefAccountant.getFirstName())) {
                persistedChiefAccountant.setFirstName(updatedChiefAccountant.getFirstName());
            }
        }
        else {
            throw new InvalidParameterException("First name (" + updatedChiefAccountant.getFirstName() + ") is invalid");
        }

        // Check if last name is valid
        if(updatedChiefAccountant.getLastName().length() != 0) {
            if(!Objects.equals(persistedChiefAccountant.getLastName(), updatedChiefAccountant.getLastName())) {
                persistedChiefAccountant.setLastName(updatedChiefAccountant.getLastName());
            }
        }
        else {
            throw new InvalidParameterException("Last name (" + updatedChiefAccountant.getLastName() + ") is invalid");
        }

        // Check if email is valid
        if(updatedChiefAccountant.getEmail().length() != 0) {
            if(!Objects.equals(persistedChiefAccountant.getEmail(), updatedChiefAccountant.getEmail())) {
                if(!CA_REPOSITORY.existsChiefAccountantByEmail(updatedChiefAccountant.getEmail())) {
                    persistedChiefAccountant.setEmail(updatedChiefAccountant.getEmail());
                }
                else {
                    throw new InvalidParameterException("Email (" + updatedChiefAccountant.getEmail() + ") is taken");
                }
            }
        }
        else {
            throw new InvalidParameterException("Email (" + updatedChiefAccountant.getEmail() + ") is invalid");
        }

        return persistedChiefAccountant;
    }

    public void delete(int employeeId) {
        if(!CA_REPOSITORY.existsById(employeeId)) {
            throw new ChiefAccountantNotFoundException("Chief accountant with id (" + employeeId + ") does not exist");
        }
        CA_REPOSITORY.deleteById(employeeId);
    }
}

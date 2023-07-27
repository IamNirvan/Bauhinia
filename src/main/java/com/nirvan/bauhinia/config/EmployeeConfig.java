package com.nirvan.bauhinia.config;

import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.employee.AccountType;
import com.nirvan.bauhinia.employee.administrator.Administrator;
import com.nirvan.bauhinia.employee.administrator.AdministratorRepository;
import com.nirvan.bauhinia.employee.chiefaccountant.ChiefAccountant;
import com.nirvan.bauhinia.employee.chiefaccountant.ChiefAccountantRepository;
import com.nirvan.bauhinia.employee.inventoryclerk.InventoryClerk;
import com.nirvan.bauhinia.employee.inventoryclerk.InventoryClerkRepository;
import com.nirvan.bauhinia.employee.productionmanager.ProductionManager;
import com.nirvan.bauhinia.employee.productionmanager.ProductionManagerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EmployeeConfig {

    @Bean
    public CommandLineRunner employeeConfiguration(
        AdministratorRepository administratorRepository,
        ChiefAccountantRepository chiefAccountantRepository,
        InventoryClerkRepository inventoryClerkRepository,
        ProductionManagerRepository productionManagerRepository
    ) {
        return args -> {
            Administrator admin = new Administrator("Sam", "Johnson", "SamJohnson@bauhinia.com", "Sam123");
            admin.setAccountType(AccountType.ADMINISTRATOR);
            if(!administratorRepository.existsAdministratorByEmail(admin.getEmail())) {
                administratorRepository.save(admin);
            }

            ChiefAccountant accountant = new ChiefAccountant("Jessica", "Lambert", "JessicaLambert@bauhinia.com", "Jessica123");
            accountant.setAccountType(AccountType.CHIEF_ACCOUNTANT);
            if(!chiefAccountantRepository.existsChiefAccountantByEmail(accountant.getEmail())) {
                chiefAccountantRepository.save(accountant);
            }

            InventoryClerk inventoryClerk = new InventoryClerk("Ed", "Lambert", "EdLambert@bauhinia.com", "Ed123");
            inventoryClerk.setAccountType(AccountType.INVENTORY_CLERK);
            if(!inventoryClerkRepository.existsInventoryClerkByEmail(inventoryClerk.getEmail())) {
                inventoryClerkRepository.save(inventoryClerk);
            }

            ProductionManager productionManager = new ProductionManager("Bruce", "Wayne", "BruceWayne@bauhinia.com", "Bruce123");
            productionManager.setAccountType(AccountType.PRODUCTION_MANAGER);
            if(!productionManagerRepository.existsProductionManagerByEmail(productionManager.getEmail())) {
                productionManagerRepository.save(productionManager);
            }
        };
    }
}

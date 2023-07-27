package com.nirvan.bauhinia.config;

import com.nirvan.bauhinia.address.Address;
import com.nirvan.bauhinia.customer.Customer;
import com.nirvan.bauhinia.customer.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CustomerConfig {
    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {
            Customer customer1 = new Customer(
                    "Samantha",
                    "Meyers",
                    "Samantha@gmail.com",
                    "Samantha123",
                    "0712222132",
                    "0712219928"
            );
            Customer customer2 = new Customer(
                    "Kasun",
                    "Weerasinghe",
                    "Kassa@gmail.com",
                    "Kassa123",
                    "0753452132",
                    "0799869928"
            );

            List<Customer> customersToRegister = new ArrayList<>();
            customersToRegister.add(customer1);
            customersToRegister.add(customer2);

            customersToRegister.forEach(customer -> {
                if(!repository.existsCustomerByEmail(customer.getEmail())) {
                    repository.save(customer);
                }
            });
        };
    }
}

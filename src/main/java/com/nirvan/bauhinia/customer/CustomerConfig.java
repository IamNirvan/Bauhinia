package com.nirvan.bauhinia.customer;

import com.nirvan.bauhinia.address.Address;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository) {
        return args -> {

            Address customer1Address1 = new Address(
                    "Western",
                    "Colombo",
                    "Marine drive",
                    "32/12",
                    "800000");

            Customer customer1 = new Customer(
                    "Samantha",
                    "Meyers",
                    "Samantha@gmail.com",
                    "Samantha123",
                    "0712222132",
                    "0712219928",
                    List.of(customer1Address1)
            );

            Address customer2Address1 = new Address(
                    "Southern",
                    "Galle",
                    "Kalegana",
                    "13A",
                    "800000");

            Customer customer2 = new Customer(
                    "Kasun",
                    "Weerasinghe",
                    "Kassa@gmail.com",
                    "Kassa123",
                    "0753452132",
                    "0799869928",
                    List.of(customer2Address1)
            );

            repository.saveAll(List.of(customer1, customer2));
        };
    }
}

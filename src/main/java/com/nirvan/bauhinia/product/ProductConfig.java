package com.nirvan.bauhinia.product;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProductConfig {

    @Bean
    CommandLineRunner productCommandLineRunner(ProductRepository repository) {
        return args -> {
            Product product1 = new Product(
                    "Bear Apparel",
                    "Hoodie"
            );

            Product product2 = new Product(
                    "Bear Apparel",
                    "Polo t-shirt"
            );

            Product product3 = new Product(
                    "Lacoste",
                    "Polo t-shirt"
            );

            repository.saveAll(List.of(product1, product2, product3));
        };
    }

}

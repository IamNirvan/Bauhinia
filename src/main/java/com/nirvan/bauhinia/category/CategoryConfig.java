package com.nirvan.bauhinia.category;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CategoryConfig {

    @Bean
    CommandLineRunner CategoryCommandLineRunner(CategoryRepository repository) {
        return args -> {
            Category category1 = new Category("Suits");
            Category category2 = new Category("T-shirt");
            Category category3 = new Category("Chinos");
            Category category4 = new Category("Polos");
            Category category5 = new Category("Gym wear");

            repository.saveAll(List.of(category1, category2, category3, category4, category5));
        };
    }
}

//package com.nirvan.bauhinia.config;
//
//import com.nirvan.bauhinia.category.Category;
//import com.nirvan.bauhinia.category.CategoryRepository;
//import com.nirvan.bauhinia.customer.Customer;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Configuration
//public class CategoryConfig {
//
//    @Bean
//    CommandLineRunner CategoryCommandLineRunner(CategoryRepository repository) {
//        return args -> {
//            Category category1 = new Category("Suits");
//            Category category2 = new Category("T-shirt");
//            Category category3 = new Category("Chinos");
//            Category category4 = new Category("Polos");
//            Category category5 = new Category("Gym wear");
//
//
//            List<Category> categoriesToAdd = new ArrayList<>();
//            categoriesToAdd.add(category1);
//            categoriesToAdd.add(category2);
//            categoriesToAdd.add(category3);
//            categoriesToAdd.add(category4);
//            categoriesToAdd.add(category5);
//
//            categoriesToAdd.forEach(category -> {
//                if(!repository.existsCategoryByName(category.getName())) {
//                    repository.save(category);
//                }
//            });
//
//
////            repository.saveAll(List.of(category1, category2, category3, category4, category5));
//        };
//    }
//}

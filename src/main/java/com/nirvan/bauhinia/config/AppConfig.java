package com.nirvan.bauhinia.config;

import com.nirvan.bauhinia.utility.Validation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Validation validation() {
        return Validation.getInstance();
    }


}

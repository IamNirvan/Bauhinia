package com.nirvan.bauhinia.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    Boolean existsByManufacturer(String name);
    Boolean existsByProductName(String name);

}

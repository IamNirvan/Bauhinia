package com.nirvan.bauhinia.order;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrder, Integer> {

    @Query("SELECT count(o) FROM ProductOrder o")
    Optional<Integer> getProductOrderCount();
}

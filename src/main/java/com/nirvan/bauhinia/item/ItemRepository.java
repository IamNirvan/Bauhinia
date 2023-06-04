package com.nirvan.bauhinia.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    boolean existsItemBySku(String sku);

    Optional<Item> findBySku(String sku);
}

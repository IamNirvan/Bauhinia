package com.nirvan.bauhinia.item;

import com.nirvan.bauhinia.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {
    boolean existsItemBySku(String sku);

    Optional<Item> findBySku(String sku);

    @Query("SELECT i.product FROM Item i WHERE i.sku = ?1")
    Optional<Product> findProductBySku(String sku);

    @Query("SELECT i FROM Item i WHERE i.quantity > 0")
    Optional<List<Item>> findAllAvailableItems();

//    @Query("SELECT count(i) FROM Item i WHERE i.product.id = ?1")
//    Optional<Integer> findAllItemsCountByProduct(int productId);

//    @Query("SELECT count(i) FROM Item i WHERE i.order = null AND i.product.id = ?1")
//    Optional<Integer> findAvailableItemsCountByProduct(int productId);

    @Query("SELECT i.quantity FROM Item i WHERE i.quantity > 0 AND i.product.id = ?1")
    Optional<List<Integer>> findAvailableItemsByProduct(int productId);

    @Query("SELECT i FROM Item i WHERE i.size = ?1")
    Optional<List<Item>> findItemBySize(String size);

    @Query("SELECT i FROM Item i WHERE i.quantity > 0 AND  i.size = ?1 AND i.product.id = ?2")
    Optional<List<Item>> findIAllAvailableItemBySizeAndProduct(String size, int productId);

}

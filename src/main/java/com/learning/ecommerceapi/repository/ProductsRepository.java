package com.learning.ecommerceapi.repository;

import com.learning.ecommerceapi.model.entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductsRepository extends JpaRepository<Products, UUID> {
    @Query(value = "SELECT * FROM products_tb WHERE LOWER(product_name) LIKE (CONCAT('%', :product_name, '%'))", nativeQuery = true)
    Optional<Products> findProductsByProductName(@Param("product_name") String productName);

//    @Query(value = "SELECT * FROM products_tb WHERE category_id = :category_id LIMIT 1", nativeQuery = true)
//    Optional<Products> findProductsByCategory(@Param("category_id") UUID categoryId);

    @Query(value = "SELECT * FROM products_tb WHERE category_id = category_id", nativeQuery = true)
    List<Products> findAllByCategory(String category);

    @Query(value = "SELECT * FROM products_tb WHERE product_id = :product_id", nativeQuery = true)
    List<Products> findProductById(@Param("product_id") UUID productId);

    @Query(value = "SELECT * FROM products_tb WHERE product_id = :product_id", nativeQuery = true)
    List<Products> findAllByProductId(UUID productId);
}

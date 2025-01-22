package com.learning.ecommerceapi.repository;

import com.learning.ecommerceapi.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    @Query(value = "SELECT * FROM category_tb WHERE LOWER(name) LIKE LOWER(CONCAT('%', :name ,'%'))", nativeQuery = true)
    Optional<Category> findCategoryByName(@Param("name") String name);
}

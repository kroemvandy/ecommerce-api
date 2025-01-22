package com.learning.ecommerceapi.repository;

import com.learning.ecommerceapi.model.entity.OrdersProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersProductsRepository extends JpaRepository<OrdersProducts, UUID> {

}

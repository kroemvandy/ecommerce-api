package com.learning.ecommerceapi.repository;

import com.learning.ecommerceapi.model.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersRepository extends JpaRepository<Orders, UUID> {
}

package com.learning.ecommerceapi.service;

import com.learning.ecommerceapi.model.dto.request.OrderRequest;
import com.learning.ecommerceapi.model.dto.response.OrderResponse;
import com.learning.ecommerceapi.model.entity.Orders;

import java.util.List;
import java.util.UUID;

public interface OrdersService {
    List<Orders> findAllOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    Orders findOrderById(UUID orderId);
    OrderResponse createOrder(OrderRequest orderRequest);
    OrderResponse updateOrder(UUID orderId, OrderRequest orderRequest);
    void deleteOrder(UUID orderId);
}

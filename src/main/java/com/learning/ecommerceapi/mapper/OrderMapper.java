package com.learning.ecommerceapi.mapper;

import com.learning.ecommerceapi.model.dto.response.OrderResponse;
import com.learning.ecommerceapi.model.entity.Orders;

public class OrderMapper {
    public static OrderResponse toOrderResponse(Orders orders) {
        OrderResponse response = new OrderResponse();
        response.setOrderId(orders.getOrderId());
        response.setCustomerId(orders.getCustomerId());
//        response.setProductList(orders.getOrdersProducts());
        response.setTotalAmount(orders.getTotalAmount());
        response.setOrderDate(orders.getOrderDate());
        response.setStatus(orders.getStatus());
        return response;
    }
}

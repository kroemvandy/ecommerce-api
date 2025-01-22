package com.learning.ecommerceapi.mapper;

import com.learning.ecommerceapi.model.dto.response.ProductResponse;
import com.learning.ecommerceapi.model.entity.Products;


public class ProductMapper {

    public static ProductResponse mapToProductResponse(Products products) {
        ProductResponse response = new ProductResponse();
        response.setProductId(products.getProductId());
        response.setProductName(products.getProductName());
        response.setProductDescription(products.getProductDescription());
        response.setProductPrice(products.getProductPrice());
        response.setProductQuantity(products.getProductQuantity());
        response.setCategoryId(products.getCategory().getCategoryId());
        response.setCreatedAt(products.getCreatedAt());
        response.setUpdatedAt(products.getUpdatedAt());
        return response;
    }
}

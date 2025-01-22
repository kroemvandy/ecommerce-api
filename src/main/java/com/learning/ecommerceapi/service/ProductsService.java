package com.learning.ecommerceapi.service;

import com.learning.ecommerceapi.model.dto.request.ProductRequest;
import com.learning.ecommerceapi.model.dto.response.ProductResponse;
import com.learning.ecommerceapi.model.entity.Products;

import java.util.List;
import java.util.UUID;

public interface ProductsService {
    List<Products> findAllProducts(Integer pageNo, Integer pageSize, String sortBy, String sortDirection);
    Products findProductById(UUID productId);
    List<Products> findProductsByName(String productName);
    List<Products> findProductsByCategory(UUID categoryId);
    ProductResponse createProduct(ProductRequest products);
    ProductResponse updateProduct(UUID productId, ProductRequest productRequest);
    void deleteProduct(UUID productId);
}

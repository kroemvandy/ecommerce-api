package com.learning.ecommerceapi.service;

import com.learning.ecommerceapi.model.dto.request.CategoryRequest;
import com.learning.ecommerceapi.model.dto.response.CategoryResponse;
import com.learning.ecommerceapi.model.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById(UUID id);
    Category getCategoryByName(String name);
    CategoryResponse createCategory(CategoryRequest request);
    CategoryResponse updateCategory(UUID categoryId, CategoryRequest request);
    CategoryResponse deleteCategory(UUID categoryId);
}

package com.learning.ecommerceapi.mapper;

import com.learning.ecommerceapi.model.dto.request.CategoryRequest;
import com.learning.ecommerceapi.model.dto.response.CategoryResponse;
import com.learning.ecommerceapi.model.entity.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CategoryMapper {
    public static Category toCategoryEntity(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setUpdatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());
        return category;
    }

    public static CategoryResponse mapToCateResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryId(category.getCategoryId());
        categoryResponse.setName(category.getName());
        categoryResponse.setCreatedAt(category.getCreatedAt());
        categoryResponse.setUpdatedAt(category.getUpdatedAt());
        return categoryResponse;
    }

}

package com.learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.mapper.CategoryMapper;
import com.learning.ecommerceapi.model.dto.request.CategoryRequest;
import com.learning.ecommerceapi.model.dto.response.CategoryResponse;
import com.learning.ecommerceapi.model.entity.Category;
import com.learning.ecommerceapi.repository.CategoryRepository;
import com.learning.ecommerceapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CategoryServiceImplement implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public Category getCategoryById(UUID categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->new ResourceNotFountException("Category with id " + categoryId + " not found"));
    }

    @Override
    public Category getCategoryByName(String name) {
        Optional<Category> categories = Optional.ofNullable(categoryRepository.findCategoryByName(name)
                .orElseThrow(() -> new ResourceNotFountException("Category with name " + name + " not found")));
        return categories.get();
    }

    @Override
    public CategoryResponse createCategory(CategoryRequest request) {
        Category category = CategoryMapper.toCategoryEntity(request);
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCateResponse(savedCategory);
    }

    @Override
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest request) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFountException("Category with id " + categoryId + " not found"));
        category.setName(request.getName());
        Category savedCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCateResponse(savedCategory);
    }

    @Override
    public CategoryResponse deleteCategory(UUID categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFountException("Category with id " + categoryId + " not found"));
        categoryRepository.delete(category);
        return null;
    }
}

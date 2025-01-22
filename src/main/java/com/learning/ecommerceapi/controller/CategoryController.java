package com.learning.ecommerceapi.controller;

import com.learning.ecommerceapi.model.dto.request.CategoryRequest;
import com.learning.ecommerceapi.model.dto.response.ApiResponse;
import com.learning.ecommerceapi.model.dto.response.CategoryResponse;
import com.learning.ecommerceapi.model.entity.Category;
import com.learning.ecommerceapi.service.CategoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<ApiResponse<Category>>> getAllCategories() {
        ApiResponse apiResponse = ApiResponse.<List<Category>>builder()
                .message("Successfully retrieved all categories")
                .status(HttpStatus.OK)
                .payload(categoryService.getAllCategories())
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(List.of(apiResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Category>> getCategoryById(@PathVariable UUID id) {
        ApiResponse apiResponse = ApiResponse.<Category>builder()
                .message("Successfully retrieved category id " + id)
                .status(HttpStatus.OK)
                .payload(categoryService.getCategoryById(id))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ApiResponse<Category>>> getCategoryByName(@RequestParam String name) {
        ApiResponse apiResponse = ApiResponse.<List<Category>>builder()
                .message("Successfully retrieved category name " + name)
                .status(HttpStatus.OK)
                .payload(Collections.singletonList(categoryService.getCategoryByName(name)))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(Collections.singletonList(apiResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(@RequestBody CategoryRequest request) {
        ApiResponse apiResponse = ApiResponse.<CategoryResponse>builder()
                .message("Successfully created category")
                .status(HttpStatus.CREATED)
                .payload(categoryService.createCategory(request))
                .code(201)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable UUID id, @RequestBody CategoryRequest request) {
        ApiResponse apiResponse = ApiResponse.<CategoryResponse>builder()
                .message("Successfully updated category")
                .status(HttpStatus.OK)
                .payload(categoryService.updateCategory(id, request))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> deleteCategory(@PathVariable UUID id) {
        ApiResponse apiResponse = ApiResponse.<CategoryResponse>builder()
                .message("Successfully deleted category id " + id)
                .status(HttpStatus.OK)
                .payload(categoryService.deleteCategory(id))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}

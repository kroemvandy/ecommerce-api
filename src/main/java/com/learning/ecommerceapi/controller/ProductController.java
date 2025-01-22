package com.learning.ecommerceapi.controller;

import com.learning.ecommerceapi.model.dto.request.ProductRequest;
import com.learning.ecommerceapi.model.dto.response.ApiResponse;
import com.learning.ecommerceapi.model.dto.response.ProductResponse;
import com.learning.ecommerceapi.model.entity.Products;
import com.learning.ecommerceapi.service.ProductsService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/products")
@SecurityRequirement(name = "bearerAuth")
public class ProductController {

    private final ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<ApiResponse<Products>>> findAllProducts(
                                                                       @Positive @RequestParam(defaultValue = "1" ) Integer pageNo,
                                                                       @Positive @RequestParam(defaultValue = "10" ) Integer pageSize,
                                                                       @RequestParam(defaultValue = "productName" ) String sortBy,
                                                                       @RequestParam(defaultValue = "ASC" ) String sortDirection) {
        ApiResponse apiResponse = ApiResponse.<List<Products>>builder()
                .message("Successfully retrieved all products")
                .status(HttpStatus.OK)
                .payload(productsService.findAllProducts(pageNo, pageSize, sortBy, sortDirection))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(List.of(apiResponse));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<ApiResponse<Products>> findProductById(@RequestParam UUID productId) {
        ApiResponse apiResponse = ApiResponse.<Products>builder()
                .message("Successfully retrieved product id " + productId)
                .status(HttpStatus.OK)
                .payload(productsService.findProductById(productId))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @GetMapping("/name/{productName}")
    public ResponseEntity<List<ApiResponse<Products>>> findProductByName(@RequestParam String productName) {
        ApiResponse apiResponse = ApiResponse.<List<Products>>builder()
                .message("Successfully retrieved product name " + productName)
                .status(HttpStatus.OK)
                .payload(productsService.findProductsByName(productName))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(List.of(apiResponse));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<ApiResponse<Products>>> findProductByCategory(@RequestParam UUID categoryId) {
        ApiResponse apiResponse = ApiResponse.<List<Products>>builder()
                .message("Successfully retrieved product category " + categoryId)
                .status(HttpStatus.OK)
                .payload(productsService.findProductsByCategory(categoryId))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(List.of(apiResponse));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@RequestBody ProductRequest request) {
        ApiResponse apiResponse = ApiResponse.<ProductResponse>builder()
                .message("Successfully created product")
                .status(HttpStatus.CREATED)
                .payload(productsService.createProduct(request))
                .code(201)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@RequestParam UUID productId, @RequestBody ProductRequest request) {
        ApiResponse apiResponse = ApiResponse.<ProductResponse>builder()
                .message("Successfully updated product id : " + productId)
                .status(HttpStatus.OK)
                .payload(productsService.updateProduct(productId, request))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<ProductResponse>> deleteProduct(@RequestParam UUID productId) {
        productsService.deleteProduct(productId);
        ApiResponse apiResponse = ApiResponse.<ProductResponse>builder()
                .message("Successfully deleted product id : " + productId)
                .status(HttpStatus.OK)
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }
}

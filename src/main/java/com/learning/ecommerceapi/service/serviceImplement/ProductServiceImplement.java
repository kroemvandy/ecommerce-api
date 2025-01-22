package com.learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.mapper.ProductMapper;
import com.learning.ecommerceapi.model.dto.request.ProductRequest;
import com.learning.ecommerceapi.model.dto.response.ProductResponse;
import com.learning.ecommerceapi.model.entity.Category;
import com.learning.ecommerceapi.model.entity.Products;
import com.learning.ecommerceapi.repository.CategoryRepository;
import com.learning.ecommerceapi.repository.ProductsRepository;
import com.learning.ecommerceapi.service.ProductsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductServiceImplement implements ProductsService {

    private final ProductsRepository productsRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Products> findAllProducts(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        if (pageNo < 1 || pageSize < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number and page size must be greater than 0");
        }

        String defaultSortBy = "productName";
        String defaultSortByField = (sortBy == null || sortBy.isEmpty()) ? defaultSortBy : sortBy;

        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(direction, defaultSortByField));
        return productsRepository.findAll(pageable).getContent();
    }

    @Override
    public Products findProductById(UUID productId) {
        return productsRepository.findById(productId).orElseThrow(() -> new ResourceNotFountException("Product with id " + productId + " not found"));
    }

    @Override
    public List<Products> findProductsByName(String productName) {
        Optional<Products> products = Optional.ofNullable(productsRepository.findProductsByProductName(productName)).orElseThrow(() -> new ResourceNotFountException("Product with name " + productName + " not found"));
        return List.of(products.get());
    }

    @Override
    public List<Products> findProductsByCategory(UUID categoryId) {
        List<Products> products = productsRepository.findAllByCategory(categoryId.toString());
        if (products.isEmpty()) {
            throw new ResourceNotFountException("Product with category id " + categoryId + " not found");
        }
        return products;
    }

    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFountException("Category with id " + productRequest.getCategoryId() + " not found"));
        Products product = new Products();
        product.setProductName(productRequest.getProductName());
        product.setProductDescription(productRequest.getProductDescription());
        product.setProductPrice(productRequest.getProductPrice());
        product.setProductQuantity(productRequest.getProductQuantity());
        product.setCategory(category);
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());
        Products savedProduct = productsRepository.save(product);
        return ProductMapper.mapToProductResponse(savedProduct);
    }

    @Override
    public ProductResponse updateProduct(UUID productId, ProductRequest productRequest) {
        Products products = productsRepository.findById(productId).orElseThrow(() -> new ResourceNotFountException("Product with id " + productId + " not found"));
        products.setProductName(productRequest.getProductName());
        products.setProductDescription(productRequest.getProductDescription());
        products.setProductPrice(productRequest.getProductPrice());
        products.setProductQuantity(productRequest.getProductQuantity());
        products.setCategory(categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new ResourceNotFountException("Category with id " + productRequest.getCategoryId() + " not found")));
        products.setUpdatedAt(LocalDateTime.now());
        Products savedProduct = productsRepository.save(products);
        return ProductMapper.mapToProductResponse(savedProduct);
    }

    @Override
    public void deleteProduct(UUID productId) {
        Products products = productsRepository.findById(productId).orElseThrow(() -> new ResourceNotFountException("Product with id " + productId + " not found"));
        productsRepository.delete(products);
    }
}

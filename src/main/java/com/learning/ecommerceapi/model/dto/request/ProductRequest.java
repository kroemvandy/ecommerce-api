package com.learning.ecommerceapi.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    @NotNull(message = "product name cannot be null")
    private String productName;
    @NotNull(message = "product description cannot be null")
    private String productDescription;
    @NotNull(message = "product price cannot be null")
    private BigDecimal productPrice;
    @NotNull(message = "product quantity cannot be null")
    private Integer productQuantity;
    @NotNull(message = "category id cannot be null")
    private UUID categoryId;
}

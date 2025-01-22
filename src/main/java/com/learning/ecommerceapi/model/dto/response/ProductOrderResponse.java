package com.learning.ecommerceapi.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOrderResponse {
    private UUID productId;
    private String productName;
    private String productDescription;
    private BigDecimal productPrice;
    private Integer productQuantity;
}

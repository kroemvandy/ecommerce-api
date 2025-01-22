package com.learning.ecommerceapi.model.dto.request;

import com.learning.ecommerceapi.model.entity.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequest {
    @NotNull(message = "Product id is required")
    private List<UUID> productId;
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    @NotNull(message = "Status is required")
    private OrderStatus status;
    @NotNull(message = "Order date is required")
    private LocalDateTime orderDate;
}

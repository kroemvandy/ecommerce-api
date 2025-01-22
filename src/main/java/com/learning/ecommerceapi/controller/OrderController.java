package com.learning.ecommerceapi.controller;

import com.learning.ecommerceapi.model.dto.request.OrderRequest;
import com.learning.ecommerceapi.model.dto.response.ApiResponse;
import com.learning.ecommerceapi.model.dto.response.OrderResponse;
import com.learning.ecommerceapi.model.entity.Orders;
import com.learning.ecommerceapi.service.OrdersService;
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
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrdersService ordersService;

    @GetMapping
    public ResponseEntity<List<ApiResponse<Orders>>> getAllOrders(@Positive
                                                                         @RequestParam(defaultValue = "1") Integer pageNo,
                                                                  @RequestParam(defaultValue = "10") Integer pageSize,
                                                                  @RequestParam(defaultValue = "orderDate") String sortBy,
                                                                  @RequestParam(defaultValue = "ASC") String sortDirection) {
        ApiResponse apiResponse = ApiResponse.<List<Orders>>builder()
                .message("Successfully retrieved all orders")
                .status(HttpStatus.OK)
                .payload(ordersService.findAllOrders(pageNo, pageSize, sortBy, sortDirection))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(List.of(apiResponse));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<Orders>> getOrderById(@PathVariable UUID orderId) {
        ApiResponse apiResponse = ApiResponse.<Orders>builder()
                .message("Successfully retrieved order id " + orderId)
                .status(HttpStatus.OK)
                .payload(ordersService.findOrderById(orderId))
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
     }

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(@RequestBody OrderRequest request) {
        ApiResponse apiResponse = ApiResponse.<OrderResponse>builder()
                .message("Successfully created order")
                .status(HttpStatus.CREATED)
                .payload(ordersService.createOrder(request))
                .code(201)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponse<?>> deleteOrder(@PathVariable UUID orderId) {
        ordersService.deleteOrder(orderId);
        ApiResponse apiResponse = ApiResponse.<OrderResponse>builder()
                .message("Successfully deleted order id " + orderId)
                .status(HttpStatus.OK)
                .code(200)
                .localDateTime(LocalDateTime.now())
                .build();
        return ResponseEntity.ok().body(apiResponse);
    }

}

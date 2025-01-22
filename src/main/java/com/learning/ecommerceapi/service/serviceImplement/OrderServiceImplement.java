package com.learning.ecommerceapi.service.serviceImplement;

import com.learning.ecommerceapi.config.GetCurrentUserConfig;
import com.learning.ecommerceapi.exception.ResourceNotFountException;
import com.learning.ecommerceapi.mapper.OrderMapper;
import com.learning.ecommerceapi.model.dto.request.OrderRequest;
import com.learning.ecommerceapi.model.dto.response.OrderResponse;
import com.learning.ecommerceapi.model.entity.*;
import com.learning.ecommerceapi.repository.OrdersRepository;
import com.learning.ecommerceapi.repository.ProductsRepository;
import com.learning.ecommerceapi.repository.UsersRepository;
import com.learning.ecommerceapi.service.OrdersService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderServiceImplement implements OrdersService {
    private final OrdersRepository ordersRepository;
    private final GetCurrentUserConfig currentUser;
    private final ProductsRepository productsRepository;

    @Override
    public List<Orders> findAllOrders(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {
        if (pageNo < 1 || pageSize < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Page number and page size must be greater than 0");
        }

        String defaultSortBy = "orderDate";
        String defaultSortByField = (sortBy == null || sortBy.isEmpty()) ? defaultSortBy : sortBy;

        Sort.Direction direction = "ASC".equalsIgnoreCase(sortDirection) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(direction, defaultSortByField));
        return ordersRepository.findAll(pageable).getContent();
    }

    @Override
    public Orders findOrderById(UUID orderId) {
        return ordersRepository.findById(orderId).orElseThrow(() -> new ResourceNotFountException("Order with id " + orderId + " not found"));
    }

    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {
        Users user = currentUser.getCurrentUser();

        List<Products> products = productsRepository.findAllById(orderRequest.getProductId());
        if (products == null || products.isEmpty()) {
            throw new ResourceNotFountException("Products with given ids not found");
        }

        Orders orders = new Orders();
        orders.setOrderDate(LocalDate.now().atStartOfDay());
        orders.setCustomerId(user.getUserId());
        orders.setStatus(OrderStatus.PENDING);
        orders.setCreatedAt(LocalDateTime.now());
        orders.setUpdatedAt(LocalDateTime.now());

        BigDecimal totalAmountOrder = BigDecimal.ZERO;
        List<OrdersProducts> ordersProductsList = new ArrayList<>();

        for (int i = 0; i < products.size(); i++) {
            OrdersProducts ordersProducts = new OrdersProducts();
            ordersProducts.setQuantity(orderRequest.getQuantity());
            ordersProducts.setProductId(products.get(i));
            ordersProducts.setOrderId(orders);

            BigDecimal productTotalAmount = products.get(i).getProductPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity()));
            totalAmountOrder = totalAmountOrder.add(productTotalAmount);

            ordersProductsList.add(ordersProducts);
        }

        orders.setOrdersProducts(ordersProductsList);
        orders.setTotalAmount(totalAmountOrder);

        Orders savedOrder = ordersRepository.save(orders);
        return OrderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public OrderResponse updateOrder(UUID orderId, OrderRequest orderRequest) {
        return null;
    }

    @Override
    public void deleteOrder(UUID orderId) {
        Orders orders = ordersRepository.findById(orderId).orElseThrow(() -> new ResourceNotFountException("Order with id " + orderId + " not found"));
        ordersRepository.delete(orders);
    }
}

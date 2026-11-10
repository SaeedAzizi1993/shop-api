package com.saeed.shop_api.service;

import com.saeed.shop_api.dto.OrderRequest;
import com.saeed.shop_api.dto.OrderResponse;
import com.saeed.shop_api.entity.Order;
import com.saeed.shop_api.entity.OrderStatus;
import com.saeed.shop_api.entity.Product;
import com.saeed.shop_api.exception.InvalidOrderStatusException;
import com.saeed.shop_api.exception.OrderNotFoundException;
import com.saeed.shop_api.exception.OutOfStockException;
import com.saeed.shop_api.exception.ProductNotFoundException;
import com.saeed.shop_api.repository.OrderRepository;
import com.saeed.shop_api.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public OrderResponse create(OrderRequest request) {
        // 1. find the product (or 404)
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new ProductNotFoundException(request.productId()));

        // 2. enough stock? (or 409)
        if (product.getStock() < request.quantity()) {
            throw new OutOfStockException(product.getName(), request.quantity(), product.getStock());
        }

        // 3. decrease stock
        product.setStock(product.getStock() - request.quantity());
        productRepository.save(product);

        // 4. calculate total price
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(request.quantity()));

        // 5. build the order
        Order order = new Order();
        order.setProduct(product);
        order.setQuantity(request.quantity());
        order.setTotalPrice(totalPrice);
        order.setStatus(OrderStatus.PENDING);
        order.setCreatedAt(LocalDateTime.now());

        // 6. save and return the receipt
        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }

    public List<OrderResponse> findAll() {
        List<OrderResponse> orders = new ArrayList<>();
        for (Order order : orderRepository.findAll()) {
            orders.add(OrderResponse.from(order));
        }
        return orders;
    }

    public OrderResponse findById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        return OrderResponse.from(order);
    }

    @Transactional
    public OrderResponse updateStatus(Long id, OrderStatus newStatus) {
        // 1. find the order (or 404)
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        // 2. only PENDING orders can change (or 409)
        if (order.getStatus() != OrderStatus.PENDING || newStatus == OrderStatus.PENDING) {
            throw new InvalidOrderStatusException(order.getStatus(), newStatus);
        }

        // 3. cancelled? give the stock back
        if (newStatus == OrderStatus.CANCELLED) {
            Product product = order.getProduct();
            product.setStock(product.getStock() + order.getQuantity());
            productRepository.save(product);
        }

        // 4. save the new status
        order.setStatus(newStatus);
        Order saved = orderRepository.save(order);
        return OrderResponse.from(saved);
    }
}

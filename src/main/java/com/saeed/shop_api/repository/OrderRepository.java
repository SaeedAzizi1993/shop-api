package com.saeed.shop_api.repository;

import com.saeed.shop_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
    boolean existsByProductId(Long productId);
}

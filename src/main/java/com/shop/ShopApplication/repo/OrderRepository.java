package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}

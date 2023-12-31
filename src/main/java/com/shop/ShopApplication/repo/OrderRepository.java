package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.Order;
import com.shop.ShopApplication.entity.RestaurantTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
    List<Order> findByUserId(Long userId);

    @Query("SELECT o FROM Order o WHERE o.table.table_id = :tableId ORDER BY o.orderDate DESC")
    Optional<Order> findLatestOrderByTableId(Long tableId);
}


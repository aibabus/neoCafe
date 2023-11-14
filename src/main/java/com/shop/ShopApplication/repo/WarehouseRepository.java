package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.WarehouseItem;
import com.shop.ShopApplication.entity.enums.StockCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WarehouseRepository extends JpaRepository<WarehouseItem, Long> {

    @Query("SELECT w FROM WarehouseItem w WHERE w.stockCategory = :stockCategory")
    List<WarehouseItem> findByStockCategory(@Param("stockCategory") StockCategory stockCategory);
}

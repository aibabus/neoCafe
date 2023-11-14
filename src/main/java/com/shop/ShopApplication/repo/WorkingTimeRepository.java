package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {
}

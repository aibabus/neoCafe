package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.WorkingTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WorkingTimeRepository extends JpaRepository<WorkingTime, Long> {
    @Query("SELECT wt FROM WorkingTime wt WHERE wt.user.user_id = :userId")
    Optional<WorkingTime> findByUser(Long userId);
}

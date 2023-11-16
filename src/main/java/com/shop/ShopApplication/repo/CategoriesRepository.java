package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {

}

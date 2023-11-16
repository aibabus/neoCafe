package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<MenuProduct, Long> {

}

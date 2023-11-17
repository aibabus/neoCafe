package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.Composition;
import com.shop.ShopApplication.entity.MenuProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompositionRepository extends JpaRepository<Composition, Long> {

    List<Composition> findByMenuProduct(MenuProduct menuProduct);
}

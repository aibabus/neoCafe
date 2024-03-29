package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.MenuProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface MenuRepository extends JpaRepository<MenuProduct, Long> {
    @Query("SELECT m FROM MenuProduct m WHERE m.categories.category_id = :categoryId")
    List<MenuProduct> findByCategories_CategoryId(Long categoryId);

    @Query("SELECT m FROM MenuProduct m WHERE m.categories.category_id = :categoryId AND m.filial.filial_id = :filialId")
    List<MenuProduct> findByCategoryIdAndFilialId(
            @Param("categoryId") Long categoryId,
            @Param("filialId") Long filialId
    );

    @Query("SELECT m FROM MenuProduct m WHERE m.filial.filial_id = :filialId")
    List<MenuProduct> findByFilialId(@Param("filialId") Long filialId);

    List<MenuProduct> findByNameContainingIgnoreCase(String name);
    @Query("SELECT m FROM MenuProduct m ORDER BY FUNCTION('RAND')")
    List<MenuProduct> findRandomProducts(Pageable pageable);

    Page<MenuProduct> findAll(Pageable pageable);
}

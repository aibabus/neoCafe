package com.shop.ShopApplication.repo;
import com.shop.ShopApplication.entity.Filial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FilialRepository extends JpaRepository<Filial, Long> {

    Optional<Filial> findById(Long id);
    @Query("SELECT f.name FROM Filial f WHERE f.filial_id = :filialId")
    Optional<String> findFilialNameByFilialId(@Param("filialId") Long filialId);
}


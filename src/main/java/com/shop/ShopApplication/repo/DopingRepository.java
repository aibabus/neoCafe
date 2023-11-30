package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.Doping;
import com.shop.ShopApplication.entity.Filial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DopingRepository extends JpaRepository<Doping, Long> {

}

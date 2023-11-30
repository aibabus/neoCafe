package com.shop.ShopApplication.dto.warehouseDTO;

import com.shop.ShopApplication.entity.enums.StockCategory;
import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveWarehouseItemDto {
    private String name;
    private StockCategory stockCategory;
    private Double quantity;
    private Double minimalLimit;
    private Unit unit;
    private Date arrivalDate;
    private Date expirationDate;
    private Long filial_id;
}

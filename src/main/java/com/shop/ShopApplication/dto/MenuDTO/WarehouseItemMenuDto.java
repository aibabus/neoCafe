package com.shop.ShopApplication.dto.MenuDTO;

import com.shop.ShopApplication.entity.enums.StockCategory;
import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WarehouseItemMenuDto {
    private Long itemId;
    private String name;
    private StockCategory stockCategory;
    private Double quantity;
    private Unit unit;
}

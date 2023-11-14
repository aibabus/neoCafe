package com.shop.ShopApplication.DTO.warehouseDTO;

import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WarehouseItemList {
    private Long item_id;
    private String name;
    private Double quantity;
    private Unit unit;
    private Double minimalLimit;
    private Date ArrivalDate;
}

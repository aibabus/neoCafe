package com.shop.ShopApplication.dto.MenuDTO;

import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DopingDto {
    private Long dopingId;
    private Double quantity;
    private Unit unit; // Assuming you have a Unit enum
    private List<WarehouseItemMenuDto> items;
    private Double price;
}

package com.shop.ShopApplication.DTO.MenuDTO;

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
public class CompositionDto {
    private Long compositionId;
    private Double quantity;
    private Unit unit; // Assuming you have a Unit enum
    private List<WarehouseItemMenuDto> items;
}

package com.shop.ShopApplication.DTO.MenuDTO;

import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompositionRequest {
    private Double quantity;
    private Unit unit;
    private Long itemId;
}

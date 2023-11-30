package com.shop.ShopApplication.dto.MenuDTO;

import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DopingRequest {
    private Double quantity;
    private Unit unit;
    private Double price;
    private Long itemId;

}

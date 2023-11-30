package com.shop.ShopApplication.service.menuService.responses;

import com.shop.ShopApplication.entity.MenuProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String message;
    private Boolean isSucceed;
    private MenuProduct menuProduct;
}

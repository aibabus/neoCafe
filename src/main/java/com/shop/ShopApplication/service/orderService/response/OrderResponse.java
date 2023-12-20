package com.shop.ShopApplication.service.orderService.response;

import com.shop.ShopApplication.entity.MenuProduct;
import com.shop.ShopApplication.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String message;
    private Boolean isSucceed;
    private Order order;
    private Double bonusPlus;
    private Double finalPrice;
}

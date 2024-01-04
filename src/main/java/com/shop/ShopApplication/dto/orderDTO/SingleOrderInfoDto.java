package com.shop.ShopApplication.dto.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleOrderInfoDto {
    private Long order_id;
    private String filialName;
    private Date orderDate;
    private List<OrderDetailDto> menuProducts;
    private double minusBonus;
    private double totalPrice;
    private boolean isInside;

}

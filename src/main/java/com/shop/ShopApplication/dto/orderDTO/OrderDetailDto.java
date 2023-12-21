package com.shop.ShopApplication.dto.orderDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDetailDto {
    private String menuProductName;
    private double menuProductPrice;
    private String menuProductCategoryName;
    private int quantity;
    private double finalPrice;
    private String image;
}

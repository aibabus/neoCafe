package com.shop.ShopApplication.dto.MenuDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuListDto {
    private Long productId;
    private String name;
    private String categoryName;
    private double price;
    private String image;
    private Long filial_id;
}

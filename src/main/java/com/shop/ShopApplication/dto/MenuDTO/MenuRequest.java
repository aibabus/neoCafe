package com.shop.ShopApplication.dto.MenuDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private String name;
    private Long categoryId;
    private Double price;
    private String description;
    private Long filialId;
}

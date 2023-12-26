package com.shop.ShopApplication.dto.MenuDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryListDto {
    private Long categoryId;
    private String categoryName;
    private String image;
}

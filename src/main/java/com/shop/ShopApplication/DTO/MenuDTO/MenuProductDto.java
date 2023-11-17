package com.shop.ShopApplication.DTO.MenuDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuProductDto {
    private Long productId;
    private String name;
    private Double price;
    private String description;
    private String image;
    private boolean hasAdditional;
    private Long categoryId;
    private List<CompositionDto> compositions;
    private Long filialId;


}

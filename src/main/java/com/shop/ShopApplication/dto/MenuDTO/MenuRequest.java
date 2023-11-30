package com.shop.ShopApplication.dto.MenuDTO;

import com.shop.ShopApplication.entity.Doping;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<CompositionRequest> composition;
    private List<DopingRequest> dopings;
}


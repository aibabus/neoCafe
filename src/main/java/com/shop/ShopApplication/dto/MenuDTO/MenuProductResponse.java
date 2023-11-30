package com.shop.ShopApplication.dto.MenuDTO;

import com.shop.ShopApplication.entity.Composition;
import com.shop.ShopApplication.entity.MenuProduct;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuProductResponse {
    private MenuProduct product;
    private List<Composition> compositions;
}

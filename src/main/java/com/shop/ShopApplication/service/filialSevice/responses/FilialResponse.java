package com.shop.ShopApplication.service.filialSevice.responses;

import com.shop.ShopApplication.entity.Filial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilialResponse {
    private String message;
    private boolean isSucceed;
    private Filial filial;
}

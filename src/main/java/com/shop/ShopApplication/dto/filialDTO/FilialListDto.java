package com.shop.ShopApplication.dto.filialDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FilialListDto {
    private long filial_id;
    private String name;
    private String address;
    private boolean isOpen;
}

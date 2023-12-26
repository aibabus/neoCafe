package com.shop.ShopApplication.dto.tableDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableListDto {
    private Long tableId;
    private boolean isAvailable;
}

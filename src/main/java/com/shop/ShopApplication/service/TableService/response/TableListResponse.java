package com.shop.ShopApplication.service.TableService.response;

import com.shop.ShopApplication.dto.tableDTO.TableListDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableListResponse {
    private String message;
    private boolean isSucceed;
    private List<TableListDto> tablesList;
}

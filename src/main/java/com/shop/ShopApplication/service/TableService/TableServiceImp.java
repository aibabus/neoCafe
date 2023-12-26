package com.shop.ShopApplication.service.TableService;

import com.shop.ShopApplication.dto.tableDTO.TableListDto;
import com.shop.ShopApplication.entity.RestaurantTable;
import com.shop.ShopApplication.repo.TableRepository;
import com.shop.ShopApplication.service.TableService.response.TableListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImp implements TableService{
    private final TableRepository tableRepository;

    @Override
    public TableListResponse getAllTables() {
        List<RestaurantTable> tables = tableRepository.findAll();

        if(tables.isEmpty()){
            return TableListResponse.builder()
                    .message("Столов не найдено !")
                    .isSucceed(false)
                    .build();
        }

        List<TableListDto> tableDtos = tables.stream()
                .map(table -> TableListDto.builder()
                        .tableId(table.getTable_id())
                        .isAvailable(table.isAvailable())
                        .build())
                .collect(Collectors.toList());

        return TableListResponse.builder()
                .message("Successfully retrieved tables")
                .isSucceed(true)
                .tablesList(tableDtos)
                .build();
    }

}

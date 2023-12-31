package com.shop.ShopApplication.service.TableService;

import com.shop.ShopApplication.dto.tableDTO.TableOrderDto;
import com.shop.ShopApplication.service.TableService.response.TableListResponse;

import java.util.List;

public interface TableService {
    public TableListResponse getAllTables();
    public List<TableOrderDto> getAllTablesWithLastOrders();
}

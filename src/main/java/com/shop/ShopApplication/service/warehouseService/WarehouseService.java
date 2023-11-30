package com.shop.ShopApplication.service.warehouseService;

import com.shop.ShopApplication.dto.warehouseDTO.SaveWarehouseItemDto;
import com.shop.ShopApplication.dto.warehouseDTO.WarehouseItemList;
import com.shop.ShopApplication.service.warehouseService.responses.ItemResponse;

import java.util.List;

public interface WarehouseService {
    public ItemResponse saveItem(SaveWarehouseItemDto warehouseItemDto);
    public List<WarehouseItemList> allWarehouseItemsSyrie();
    public List<WarehouseItemList> allWarehouseItems();
}

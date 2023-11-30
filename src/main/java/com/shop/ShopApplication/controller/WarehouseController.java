package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.warehouseDTO.SaveWarehouseItemDto;
import com.shop.ShopApplication.dto.warehouseDTO.WarehouseItemList;
import com.shop.ShopApplication.service.warehouseService.WarehouseService;
import com.shop.ShopApplication.service.warehouseService.responses.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/warehouse")
@RequiredArgsConstructor
public class WarehouseController {
    private final WarehouseService warehouseService;

    @PostMapping("/saveItem")
    public ResponseEntity<ItemResponse> saveItem(@RequestBody SaveWarehouseItemDto saveWarehouseItemDto) {
        return ResponseEntity.ok(warehouseService.saveItem(saveWarehouseItemDto));
    }

    @GetMapping("/syrieList")
    public ResponseEntity<List<WarehouseItemList>> syrieList() {
        return ResponseEntity.ok(warehouseService.allWarehouseItemsSyrie());
    }

    @GetMapping("/itemList")
    public ResponseEntity<List<WarehouseItemList>> itemList() {
        return ResponseEntity.ok(warehouseService.allWarehouseItems());
    }

}

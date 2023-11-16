package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.service.menuService.MenuService;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/admin/saveCategory")
    public ResponseEntity<MenuResponse> saveCategory(@RequestParam String name){
        return ResponseEntity.ok(menuService.saveCategory(name));
    }
}

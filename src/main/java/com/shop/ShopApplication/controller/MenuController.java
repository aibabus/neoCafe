package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.MenuDTO.*;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.service.menuService.MenuService;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PostMapping("/admin/saveCategory")
    public ResponseEntity<MenuResponse> saveCategory(@RequestParam String name, MultipartFile image) throws IOException {
        return ResponseEntity.ok(menuService.saveCategory(name, image));
    }

    @PutMapping("/admin/updateCategory")
    public ResponseEntity<MenuResponse> updateCategory(@RequestParam Long category_id,
                                                       @RequestParam String name,
                                                       @RequestParam MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(menuService.updateCategory(category_id, name, imageFile));
    }

    @GetMapping("/admin/allCategory")
    public ResponseEntity<List<CategoryListDto>> allCategory(){
        return ResponseEntity.ok(menuService.getAllCategories());
    }

    @DeleteMapping("/admin/deleteCategory")
    public ResponseEntity<MenuResponse> deleteCategory(@RequestParam Long category_id){
        return ResponseEntity.ok(menuService.deleteCategory(category_id));
    }


    @PostMapping("/admin/saveProduct")
    public ResponseEntity<MenuResponse> saveMenuProductWithComposition(@RequestBody MenuCompositionRequest menuRequest) throws IOException {
        return ResponseEntity.ok(menuService.saveMenuItem(menuRequest));
    }

    @PostMapping("/admin/saveProductImage")
    public ResponseEntity<MenuResponse> saveMenuProductImage(@RequestParam Long product_id,
                                                             @RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok(menuService.addImageToMenuProduct(product_id, image));

    }
    @PutMapping("/admin/update/menuProduct")
    public ResponseEntity<MenuResponse> updateMenuProduct(@RequestParam Long product_id,
                                                             @RequestBody MenuCompositionRequest menuRequest) throws IOException {
        return ResponseEntity.ok(menuService.updateMenuItemWithComposition(product_id, menuRequest));

    }

    @DeleteMapping("/admin/deleteMenuProduct")
    public ResponseEntity<MenuResponse> deleteProduct(@RequestParam Long product_id){
        return ResponseEntity.ok(menuService.deleteMenuProduct(product_id));
    }


    @GetMapping("/admin/getSingleMenuProduct")
    public ResponseEntity<MenuResponse> getSingleMenuProduct(@RequestParam Long product_id){
        return ResponseEntity.ok(menuService.getMenuProductById(product_id));
    }


    @GetMapping("/admin/allMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProductsByCategory(@RequestParam Long category_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProducts(category_id);
        return ResponseEntity.ok(menuProducts);
    }
    @GetMapping("/admin/allMenuProducts")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProducts() {
        List<MenuProductDto> menuProducts = menuService.getAllMenuProducts();
        return ResponseEntity.ok(menuProducts);
    }

    @GetMapping("/admin/allFilialMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllFilialMenuProductsByCategory(@RequestParam Long category_id,
                                                                                   @RequestParam Long filial_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProductsByCategoryAndFilial(category_id, filial_id);
        return ResponseEntity.ok(menuProducts);
    }

    @GetMapping("/admin/search")
    public ResponseEntity<List<MenuListDto>> searchMenuProducts(@RequestParam String name) {
        List<MenuListDto> dtos = menuService.searchMenuProductsByName(name);
        if (dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }
}



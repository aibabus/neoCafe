package com.shop.ShopApplication.service.menuService;

import com.shop.ShopApplication.dto.MenuDTO.*;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.entity.MenuProduct;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    public MenuResponse saveCategory(String name, MultipartFile imageFile) throws IOException;
    public MenuResponse updateCategory(Long categoryId, String name, MultipartFile imageFile) throws IOException;
    public List<CategoryListDto> getAllCategories();
    public MenuResponse deleteCategory(Long categoryId);
    public MenuResponse addImageToMenuProduct(Long productId, MultipartFile imageFile) throws IOException;
    public MenuResponse updateMenuItemWithComposition(Long productId, MenuCompositionRequest menuRequest) throws IOException;
    public MenuResponse deleteMenuProduct(Long productId);
    public MenuResponse getMenuProductById(Long productId);
    public List<MenuProductDto> getMenuProducts(Long categoryId);
    public List<MenuProductDto> getAllMenuProducts();
    public List<MenuProductDto> getMenuProductsByCategoryAndFilial(
            Long categoryId,
            Long filialId
    );
    public List<MenuListDto> getAddtionalMenuProducts();
    public List<MenuListDto> searchMenuProductsByName(String name);
    public MenuResponse saveMenuItem(MenuCompositionRequest menuRequest) throws IOException;
}

package com.shop.ShopApplication.service.menuService;

import com.shop.ShopApplication.dto.MenuDTO.MenuProductDto;
import com.shop.ShopApplication.dto.MenuDTO.MenuRequest;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MenuService {
    public MenuResponse saveCategory(String name, MultipartFile imageFile) throws IOException;
    public MenuResponse updateCategory(Long categoryId, String name, MultipartFile imageFile) throws IOException;
    public List<Categories> getAllCategories();
    public MenuResponse deleteCategory(Long categoryId);
    public MenuResponse saveMenuItemWithComposition(MenuRequest menuRequest) throws IOException;
    public MenuResponse addImageToMenuProduct(Long productId, MultipartFile imageFile) throws IOException;
    public MenuResponse updateMenuItemWithComposition(Long productId, MenuRequest menuRequest) throws IOException;
    public MenuResponse deleteMenuProduct(Long productId);
    public MenuResponse getMenuProductById(Long productId);
    public List<MenuProductDto> getMenuProducts(Long categoryId);
    public List<MenuProductDto> getAllMenuProducts();
    public List<MenuProductDto> getMenuProductsByCategoryAndFilial(
            Long categoryId,
            Long filialId
    );
}

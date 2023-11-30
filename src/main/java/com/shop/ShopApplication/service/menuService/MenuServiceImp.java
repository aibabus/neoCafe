package com.shop.ShopApplication.service.menuService;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shop.ShopApplication.dto.MenuDTO.*;
import com.shop.ShopApplication.entity.*;
import com.shop.ShopApplication.repo.*;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuServiceImp implements MenuService{

    private final MenuRepository menuRepository;
    private final CompositionRepository compositionRepository;
    private final CategoriesRepository categoriesRepository;
    private final FilialRepository filialRepository;
    private final WarehouseRepository warehouseRepository;
    private final Cloudinary cloudinary;

    @Override
    public MenuResponse saveCategory(String name, MultipartFile imageFile) throws IOException {
        if (name.isEmpty()){
            return MenuResponse.builder()
                    .message("Не найдено название категории !")
                    .build();
        }
        File file = convert(imageFile);

        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if(!Files.deleteIfExists(file.toPath())){
            throw new IOException("Failed to delete" + file.getName());
        }

        Categories category = Categories.builder()
                        .name(name)
                        .image(uploadResult.get("url").toString())
                        .build();
        categoriesRepository.save(category);

        return MenuResponse.builder()
                .message("Новая категория успешно добавлена !")
                .build();
    }

    @Override
    public MenuResponse updateCategory(Long categoryId, String name, MultipartFile imageFile) throws IOException {
        Optional<Categories> optionalCategory = categoriesRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Categories category = optionalCategory.get();

            if (name != null && !name.isEmpty()) {
                category.setName(name);
            }

            if (imageFile != null && !imageFile.isEmpty()) {
                File file = convert(imageFile);
                Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
                if (!Files.deleteIfExists(file.toPath())) {
                    throw new IOException("Failed to delete" + file.getName());
                }
                category.setImage(uploadResult.get("url").toString());
            }

            categoriesRepository.save(category);

            return MenuResponse.builder()
                    .message("Категория успешно обновлена!")
                    .isSucceed(true)
                    .build();
        } else {
            return MenuResponse.builder()
                    .message("Категория с указанным ID не найдена!")
                    .isSucceed(false)
                    .build();
        }
    }
    @Override
    public List<Categories> getAllCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public MenuResponse deleteCategory(Long categoryId) {
        Optional<Categories> optionalCategory = categoriesRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            Categories category = optionalCategory.get();

            categoriesRepository.delete(category);

            return MenuResponse.builder()
                    .message("Категория успешно удалена!")
                    .isSucceed(true)
                    .build();
        } else {
            return MenuResponse.builder()
                    .message("Категория с указанным ID не найдена!")
                    .isSucceed(false)
                    .build();
        }
    }

    @Override
    public MenuResponse saveMenuItemWithComposition(MenuRequest menuRequest) throws IOException {

        Optional<Categories> optionalCategories = categoriesRepository.findById(menuRequest.getCategoryId());
        if(optionalCategories.isEmpty()){
            return MenuResponse.builder()
                    .message("Такой категории не существует !")
                    .isSucceed(false)
                    .build();
        }
        Categories category = optionalCategories.get();

        Optional<Filial> optionalFilial = filialRepository.findById(menuRequest.getFilialId());
        if(optionalFilial.isEmpty()){
            return MenuResponse.builder()
                    .message("Такого филиала не существует!")
                    .isSucceed(false)
                    .build();
        }
        Filial filial = optionalFilial.get();

        MenuProduct product = MenuProduct.builder()
                .name(menuRequest.getName())
                .categories(category)
                .price(menuRequest.getPrice())
                .description(menuRequest.getDescription())
                .filial(filial)
//                .image(uploadResult.get("url").toString())
                .build();

        List<Composition> compositions = new ArrayList<>();
        for (CompositionRequest compositionRequest : menuRequest.getComposition()) {
            Optional<WarehouseItem> optionalItem = warehouseRepository.findById(compositionRequest.getItemId());
            if (optionalItem.isPresent()) {
                Composition composition = Composition.builder()
                        .quantity(compositionRequest.getQuantity())
                        .unit(compositionRequest.getUnit())
                        .menuProduct(product)
                        .items(Collections.singletonList(optionalItem.get()))
                        .build();
                compositions.add(composition);
            } else {
                return MenuResponse.builder()
                        .message("Такого продукта на складе нет !")
                        .isSucceed(false)
                        .build();
            }
        }

        product.setCompositions(compositions);
        menuRepository.save(product);

        return MenuResponse.builder()
                .message("Новый продукт с композицией был успешно добавлен в меню !")
                .isSucceed(true)
                .menuProduct(product)
                .build();
    }

    @Override
    public MenuResponse addImageToMenuProduct(Long productId, MultipartFile imageFile) throws IOException {
        Optional<MenuProduct> optionalProduct = menuRepository.findById(productId);
        if (optionalProduct.isPresent()) {
            MenuProduct product = optionalProduct.get();

            // Convert MultipartFile to File
            File file = convert(imageFile);

            // Upload the image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

            // Delete the temporary file
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Failed to delete " + file.getName());
            }

            // Update the product with the new image URL
            product.setImage(uploadResult.get("url").toString());
            menuRepository.save(product);

            return MenuResponse.builder()
                    .message("Изображение успешно добавлено к продукту!")
                    .isSucceed(true)
                    .build();
        } else {
            return MenuResponse.builder()
                    .message("Продукт с указанным ID не найден!")
                    .isSucceed(false)
                    .build();
        }
    }

    @Override
    public MenuResponse updateMenuItemWithComposition(Long productId, MenuRequest menuRequest) throws IOException {
        Optional<MenuProduct> optionalProduct = menuRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            MenuProduct product = optionalProduct.get();

            if (menuRequest.getName() != null) {
                product.setName(menuRequest.getName());
            }
            if (menuRequest.getPrice() != null) {
                product.setPrice(menuRequest.getPrice());
            }
            if (menuRequest.getDescription() != null) {
                product.setDescription(menuRequest.getDescription());
            }

            if (menuRequest.getCategoryId() != null) {
                Optional<Categories> optionalCategories = categoriesRepository.findById(menuRequest.getCategoryId());
                optionalCategories.ifPresent(product::setCategories);
            }

            if (menuRequest.getFilialId() != null) {
                Optional<Filial> optionalFilial = filialRepository.findById(menuRequest.getFilialId());
                optionalFilial.ifPresent(product::setFilial);
            }

            if (menuRequest.getComposition() != null && !menuRequest.getComposition().isEmpty()) {
                List<Composition> compositions = new ArrayList<>();
                for (CompositionRequest compositionRequest : menuRequest.getComposition()) {
                    Optional<WarehouseItem> optionalItem = warehouseRepository.findById(compositionRequest.getItemId());
                    if (optionalItem.isPresent()) {
                        Composition composition = Composition.builder()
                                .quantity(compositionRequest.getQuantity())
                                .unit(compositionRequest.getUnit())
                                .menuProduct(product)
                                .items(Collections.singletonList(optionalItem.get()))
                                .build();
                        compositions.add(composition);
                    } else {
                        return MenuResponse.builder()
                                .message("Такого продукта на складе нет!")
                                .isSucceed(false)
                                .build();
                    }
                }

                product.setCompositions(compositions);
            }

            menuRepository.save(product);

            return MenuResponse.builder()
                    .message("Продукт с композицией успешно обновлен!")
                    .isSucceed(true)
                    .build();
        } else {
            return MenuResponse.builder()
                    .message("Продукт с указанным ID не найден!")
                    .isSucceed(false)
                    .build();
        }
    }

    @Override
    public List<MenuProductDto> getMenuProducts(Long categoryId) {
        List<MenuProduct> menuProducts;
        if (categoryId != null) {
            menuProducts = menuRepository.findByCategories_CategoryId(categoryId);
        } else {
            throw new IllegalArgumentException("Такой категории не существует");
        }
        return menuProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuProductDto> getAllMenuProducts() {
        List<MenuProduct> menuProducts = menuRepository.findAll();
        return menuProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<MenuProductDto> getMenuProductsByCategoryAndFilial(
             Long categoryId,
             Long filialId
    ) {
        List<MenuProduct> menuProducts;

        if (categoryId != null && filialId != null) {
            menuProducts = menuRepository.findByCategoryIdAndFilialId(categoryId, filialId);
        } else if (categoryId != null) {
            throw new IllegalArgumentException("Таких филиалов нет!");
        } else if (filialId != null) {
            throw new IllegalArgumentException("Таких Категорий нет!");
        } else {
            throw new IllegalArgumentException("Таких филиалов или категории нет!");
        }

        return menuProducts.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    @Override
    public MenuResponse deleteMenuProduct(Long productId) {
        Optional<MenuProduct> optionalProduct = menuRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            MenuProduct product = optionalProduct.get();

            menuRepository.delete(product);

            return MenuResponse.builder()
                    .message("Продукт успешно удален из меню!")
                    .isSucceed(true)
                    .build();
        } else {
            return MenuResponse.builder()
                    .message("Продукт с указанным ID не найден!")
                    .isSucceed(false)
                    .build();
        }
    }
    private MenuProductDto convertToDto(MenuProduct menuProduct) {
        return MenuProductDto.builder()
                .productId(menuProduct.getProduct_id())
                .name(menuProduct.getName())
                .price(menuProduct.getPrice())
                .description(menuProduct.getDescription())
                .image(menuProduct.getImage())
                .hasAdditional(menuProduct.isHasAdditional())
                .categoryId(menuProduct.getCategories().getCategory_id())
                .compositions(convertCompositionsToDto(menuProduct.getCompositions()))
                .filialId(menuProduct.getFilial().getFilial_id())
                .build();
    }

    private List<CompositionDto> convertCompositionsToDto(List<Composition> compositions) {
        return compositions.stream()
                .map(composition -> CompositionDto.builder()
                        .compositionId(composition.getComposition_id())
                        .quantity(composition.getQuantity())
                        .unit(composition.getUnit())
                        .items(convertWarehouseItemsToDto(composition.getItems()))
                        .build())
                .collect(Collectors.toList());
    }

    private List<WarehouseItemMenuDto> convertWarehouseItemsToDto(List<WarehouseItem> warehouseItems) {
        return warehouseItems.stream()
                .map(warehouseItem -> WarehouseItemMenuDto.builder()
                        .itemId(warehouseItem.getItem_id())
                        .name(warehouseItem.getName())
                        .stockCategory(warehouseItem.getStockCategory())
                        .quantity(warehouseItem.getQuantity())
                        .unit(warehouseItem.getUnit())
                        .build())
                .collect(Collectors.toList());
    }





    public File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getName()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
    }

}

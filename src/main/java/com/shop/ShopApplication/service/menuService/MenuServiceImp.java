package com.shop.ShopApplication.service.menuService;

import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.repo.CategoriesRepository;
import com.shop.ShopApplication.repo.CompositionRepository;
import com.shop.ShopApplication.repo.MenuRepository;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MenuServiceImp implements MenuService{

    private final MenuRepository menuRepository;
    private final CompositionRepository compositionRepository;
    private final CategoriesRepository categoriesRepository;

    @Override
    public MenuResponse saveCategory(String name){
        if (name.isEmpty()){
            return MenuResponse.builder()
                    .message("Не найдено название категории !")
                    .build();
        }
        Categories category = Categories.builder()
                        .name(name)
                        .build();
        categoriesRepository.save(category);

        return MenuResponse.builder()
                .message("Новая категория успешно добавлена !")
                .build();
    }

}

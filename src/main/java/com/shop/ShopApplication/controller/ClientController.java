package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.MenuDTO.MenuProductDto;
import com.shop.ShopApplication.dto.filialDTO.FilialListDto;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.clientService.ClientService;
import com.shop.ShopApplication.service.clientService.responses.ClientResponse;
import com.shop.ShopApplication.service.filialSevice.FilialService;
import com.shop.ShopApplication.service.menuService.MenuService;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
    private final AuthService authService;
    private final SmsService smsService;
    private final ClientService clientService;
    private final MenuService menuService;
    private final FilialService filialService;



    @PostMapping("/client-delete")
    public ResponseEntity<ClientResponse> clientDelete(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteUser(user_id));
    }

    @DeleteMapping("/client-delete-test")
    public ResponseEntity<String> clientDeleteTest(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteTest(user_id));
    }

    @GetMapping("/menu/getSingleMenuProduct")
    public ResponseEntity<MenuResponse> getSingleMenuProductClient(@RequestParam Long product_id){
        return ResponseEntity.ok(menuService.getMenuProductById(product_id));
    }
    @GetMapping("/menu/allCategory")
    public ResponseEntity<List<Categories>> allCategoryClient(){
        return ResponseEntity.ok(menuService.getAllCategories());
    }
    @GetMapping("/menu/allMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProductsByCategoryClient(@RequestParam Long category_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProducts(category_id);
        return ResponseEntity.ok(menuProducts);
    }
    @GetMapping("/menu/allMenuProducts")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProductsClient() {
        List<MenuProductDto> menuProducts = menuService.getAllMenuProducts();
        return ResponseEntity.ok(menuProducts);
    }

    @GetMapping("/menu/allFilialMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllFilialMenuProductsByCategoryClient(@RequestParam Long category_id,
                                                                                         @RequestParam Long filial_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProductsByCategoryAndFilial(category_id, filial_id);
        return ResponseEntity.ok(menuProducts);
    }
    @GetMapping("/filial/filialList")
    public ResponseEntity<List<FilialListDto>> filialList(){
        return ResponseEntity.ok(filialService.allFilial());
    }

}

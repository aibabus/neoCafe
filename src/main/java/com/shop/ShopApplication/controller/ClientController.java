package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.MenuDTO.MenuProductDto;
import com.shop.ShopApplication.dto.filialDTO.FilialListDto;
import com.shop.ShopApplication.dto.filialDTO.FilialStatusDto;
import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.entity.Order;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.orderService.OrderSevice;
import com.shop.ShopApplication.orderService.requests.CreateOrderRequest;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.clientService.ClientService;
import com.shop.ShopApplication.service.clientService.responses.ClientProfileResponse;
import com.shop.ShopApplication.service.clientService.responses.ClientResponse;
import com.shop.ShopApplication.service.filialSevice.FilialService;
import com.shop.ShopApplication.service.filialSevice.FilialStatusService;
import com.shop.ShopApplication.service.menuService.MenuService;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final FilialStatusService filialStatusService;
    private final OrderSevice orderSevice;
    private final UserRepository userRepository;


    @GetMapping("/profile")
    public ResponseEntity<ClientProfileResponse> clientProfile(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.clientProfile(user_id));
    }
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

    @GetMapping("/filial/filialStatusList")
    public ResponseEntity<List<FilialStatusDto>> filialsStatus(){
        return ResponseEntity.ok(filialStatusService.getFilialStatusForAllFiliales());
    }

    @PostMapping("/order/createOrder")
    public ResponseEntity<Order> order(@RequestBody CreateOrderRequest request){
        return ResponseEntity.ok(orderSevice.createOrder(request.getUserId(), request.getOrderItems()));
    }
}


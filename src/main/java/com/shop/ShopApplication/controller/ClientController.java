package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.MenuDTO.CategoryListDto;
import com.shop.ShopApplication.dto.MenuDTO.MenuListDto;
import com.shop.ShopApplication.dto.MenuDTO.MenuProductDto;
import com.shop.ShopApplication.dto.filialDTO.FilialListDto;
import com.shop.ShopApplication.dto.filialDTO.FilialStatusDto;
import com.shop.ShopApplication.dto.orderDTO.OrderInfoDto;
import com.shop.ShopApplication.dto.orderDTO.SingleOrderInfoDto;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.entity.Order;
import com.shop.ShopApplication.service.orderService.OrderSevice;
import com.shop.ShopApplication.service.orderService.requests.CreateOrderRequest;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.clientService.ClientService;
import com.shop.ShopApplication.service.clientService.responses.ClientProfileResponse;
import com.shop.ShopApplication.service.clientService.responses.ClientResponse;
import com.shop.ShopApplication.service.filialSevice.FilialService;
import com.shop.ShopApplication.service.filialSevice.FilialStatusService;
import com.shop.ShopApplication.service.menuService.MenuService;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
import com.shop.ShopApplication.service.orderService.response.OrderResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import io.swagger.v3.oas.annotations.Operation;
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
    private final FilialStatusService filialStatusService;
    private final OrderSevice orderSevice;
    private final UserRepository userRepository;


    @Operation(summary = "Отправляет профиль клиента")
    @GetMapping("/profile")
    public ResponseEntity<ClientProfileResponse> clientProfile(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.clientProfile(user_id));
    }
    @Operation(summary = "Удаление аккаунта клиента")
    @PostMapping("/client-delete")
    public ResponseEntity<ClientResponse> clientDelete(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteUser(user_id));
    }

    @DeleteMapping("/client-delete-test")
    public ResponseEntity<String> clientDeleteTest(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteTest(user_id));
    }


    @Operation(summary = "Полная информация одного продукта из меню")
    @GetMapping("/menu/getSingleMenuProduct")
    public ResponseEntity<MenuResponse> getSingleMenuProductClient(@RequestParam Long product_id){
        return ResponseEntity.ok(menuService.getMenuProductById(product_id));
    }
    @Operation(summary = "Все категории")
    @GetMapping("/menu/allCategory")
    public ResponseEntity<List<CategoryListDto>> allCategoryClient(){
        return ResponseEntity.ok(menuService.getAllCategories());
    }
    @Operation(summary = "Продукты из меню по категории без филиала")
    @GetMapping("/menu/allMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProductsByCategoryClient(@RequestParam Long category_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProducts(category_id);
        return ResponseEntity.ok(menuProducts);
    }
    @Operation(summary = "Список всего меню, отправляет первые 10 продуктов")
    @GetMapping("/menu/allMenuProducts")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProductsClient() {
        List<MenuProductDto> menuProducts = menuService.getAllMenuProducts();
        return ResponseEntity.ok(menuProducts);
    }

    @Operation(summary = "Поиск продуктов из меню по названию")
    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuListDto>> searchMenuProducts(@RequestParam String name) {
        List<MenuListDto> dtos = menuService.searchMenuProductsByName(name);
        if (dtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dtos);
    }

    @Operation(summary = "Список приятных дополнений, отправляет первые 5 дополнений")
    @GetMapping("/menu/additionalMenuList")
    public ResponseEntity<List<MenuListDto>> getAdditionalMenuList() {
        List<MenuListDto> dtos = menuService.getAddtionalMenuProducts();
        return ResponseEntity.ok(dtos);
    }
    @Operation(summary = "Меню по категории в конкретном филиале")
    @GetMapping("/menu/allFilialMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllFilialMenuProductsByCategoryClient(@RequestParam Long category_id,
                                                                                         @RequestParam Long filial_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProductsByCategoryAndFilial(category_id, filial_id);
        return ResponseEntity.ok(menuProducts);
    }
    @Operation(summary = "Список филиалов для дропдауна")
    @GetMapping("/filial/filialList")
    public ResponseEntity<List<FilialListDto>> filialList(){
        return ResponseEntity.ok(filialService.allFilial());
    }

    @Operation(summary = "Список филиалов вместе со статусами этих филиалов для вкладки филиалы у клиента")
    @GetMapping("/filial/filialStatusList")
    public ResponseEntity<List<FilialStatusDto>> filialsStatus(){
        return ResponseEntity.ok(filialStatusService.getFilialStatusForAllFiliales());
    }

    @Operation(summary = "Создание нового заказа")
    @PostMapping("/order/createOrder")
    public ResponseEntity<OrderResponse> order(@RequestBody CreateOrderRequest request){
        return ResponseEntity.ok(orderSevice.createOrder(request.getUserId(), request.getFilial_id(), request.getMinusBonus(), request.getOrderItems(), request.isInside()));
    }
    @Operation(summary = "Список всех заказов")
    @GetMapping("/order/allUserOrders")
    public ResponseEntity<List<OrderInfoDto>> orderList(@RequestParam Long user_id){
        return ResponseEntity.ok(orderSevice.getAllUserOrders(user_id));
    }


    @Operation(summary = "Отмена заказа")
    @PutMapping("/order/cancelOrder")
    public ResponseEntity<OrderResponse> cancelOrder(@RequestParam Long order_id){
        return ResponseEntity.ok(orderSevice.cancelOrder(order_id));
    }

    @Operation(summary = "Информация заказа")
    @GetMapping("/order/singleOrderInfo")
    public ResponseEntity<SingleOrderInfoDto> singleOrder(@RequestParam Long order_id){
        return ResponseEntity.ok(orderSevice.getOrderInfo(order_id));
    }

}


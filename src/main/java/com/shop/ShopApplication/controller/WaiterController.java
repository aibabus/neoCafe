package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.MenuDTO.CategoryListDto;
import com.shop.ShopApplication.dto.MenuDTO.MenuProductDto;
import com.shop.ShopApplication.dto.adminDTO.AdminLoginDto;
import com.shop.ShopApplication.dto.employeeDTO.WaiterLoginDto;
import com.shop.ShopApplication.dto.waiterDTO.WaiterProfileDto;
import com.shop.ShopApplication.entity.Categories;
import com.shop.ShopApplication.jwt.JwtService;
import com.shop.ShopApplication.service.TableService.TableService;
import com.shop.ShopApplication.service.TableService.response.TableListResponse;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.service.menuService.MenuService;
import com.shop.ShopApplication.service.orderService.OrderSevice;
import com.shop.ShopApplication.service.orderService.requests.CreateOrderRequest;
import com.shop.ShopApplication.service.orderService.requests.CreateOrderRequestWaiter;
import com.shop.ShopApplication.service.orderService.response.OrderResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import com.shop.ShopApplication.service.waiterService.WaiterService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/waiter")
@RequiredArgsConstructor
public class WaiterController {
    private final AuthService authService;
    private final SmsService smsService;
    private final JwtService jwtService;
    private final WaiterService waiterService;
    private final MenuService menuService;
    private final OrderSevice orderSevice;
    private final TableService tableService;

    @Operation(summary = "Профиль официанта")
    @GetMapping("/profile")
    public ResponseEntity<WaiterProfileDto> getUserProfile(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7); // Remove "Bearer "
        String username = jwtService.getUsernameFromToken(token);
        WaiterProfileDto profileDto = waiterService.getUserProfile(username);
        return profileDto != null ? ResponseEntity.ok(profileDto) : ResponseEntity.notFound().build();
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

    @Operation(summary = "Меню по категории в конкретном филиале")
    @GetMapping("/menu/allFilialMenuByCategory")
    public ResponseEntity<List<MenuProductDto>> getAllFilialMenuProductsByCategoryClient(@RequestParam Long category_id,
                                                                                         @RequestParam Long filial_id) {
        List<MenuProductDto> menuProducts = menuService.getMenuProductsByCategoryAndFilial(category_id, filial_id);
        return ResponseEntity.ok(menuProducts);
    }

    @Operation(summary = "Список всего меню, отправляет первые 10 продуктов")
    @GetMapping("/menu/allMenuProducts")
    public ResponseEntity<List<MenuProductDto>> getAllMenuProductsClient() {
        List<MenuProductDto> menuProducts = menuService.getAllMenuProducts();
        return ResponseEntity.ok(menuProducts);
    }

    @Operation(summary = "Создание нового заказа")
    @PostMapping("/order/createOrder")
    public ResponseEntity<OrderResponse> order(HttpServletRequest httpServletRequest, @RequestBody CreateOrderRequestWaiter request){
        String token = httpServletRequest.getHeader("Authorization").substring(7);
        String username = jwtService.getUsernameFromToken(token);
        return ResponseEntity.ok(orderSevice.createOrderWaiter(username, request.getFilialId(), request.getTableId(), request.getOrderItems()));
    }

    @Operation(summary = "Список столов, выводит id стола и boolean (свободен ли он)")
    @GetMapping("/table/allTables")
    public ResponseEntity<TableListResponse> listOfTables(){
        return ResponseEntity.ok(tableService.getAllTables());
    }


}

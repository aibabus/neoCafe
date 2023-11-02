package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.DTO.AdminLoginDto;
import com.shop.ShopApplication.register.AuthResponse;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @PostMapping("/saveAdmin")
    public ResponseEntity<AdminLoginDto> findUser(@RequestBody AdminLoginDto adminLoginDto) {
        AuthResponse adminDto = adminService.saveAdmin(adminLoginDto);
        return ResponseEntity.ok(adminLoginDto);
    }

    @PostMapping("/log")
    public ResponseEntity<AdminAuthResponse> login(@RequestBody AdminLoginDto request) {
        return ResponseEntity.ok(adminService.login(request));
    }

}

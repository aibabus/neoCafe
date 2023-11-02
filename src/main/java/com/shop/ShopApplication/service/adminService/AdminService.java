package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.DTO.AdminLoginDto;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.register.AuthResponse;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;


import java.util.List;

public interface AdminService {
    AuthResponse saveAdmin (AdminLoginDto adminLoginDto);
    public AdminAuthResponse login(AdminLoginDto request);

}

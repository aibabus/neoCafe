package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.DTO.AdminLoginDto;
import com.shop.ShopApplication.DTO.EmployeeList;
import com.shop.ShopApplication.DTO.EmployeeRegisterDto;
import com.shop.ShopApplication.DTO.SingleEmployeeDto;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;

import java.util.List;

public interface AdminService {
    AuthResponse saveAdmin (AdminLoginDto adminLoginDto);
    public AdminAuthResponse login(AdminLoginDto request);
    public String saveWaiter(EmployeeRegisterDto employeeRegisterDto);
    public List<EmployeeList> employeeList();
    public SingleEmployeeDto getEmployeeDetails(Long userId);

}

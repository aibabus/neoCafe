package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.DTO.*;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;

import java.util.List;

public interface AdminService {
    AuthResponse saveAdmin (AdminLoginDto adminLoginDto);
    public AdminAuthResponse login(AdminLoginDto request);
    public String saveEmployee(EmployeeRegisterDto employeeRegisterDto);
    public List<EmployeeList> employeeList();
    public SingleEmployeeDto getEmployeeDetails(Long userId);
    public String updateEmployee(EmployeeUpdateDto employeeUpdateDto);
    public String deleteEmployee(Long user_id);

}

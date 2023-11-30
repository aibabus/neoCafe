package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.dto.adminDTO.AdminLoginDto;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeList;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeRegisterDto;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeUpdateDto;
import com.shop.ShopApplication.dto.employeeDTO.SingleEmployeeDto;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;

import java.text.ParseException;
import java.util.List;

public interface AdminService {
    AuthResponse saveAdmin (AdminLoginDto adminLoginDto);
    public AdminAuthResponse login(AdminLoginDto request);
    public String saveEmployee(EmployeeRegisterDto employeeRegisterDto) throws ParseException;
    public List<EmployeeList> employeeList();
    public SingleEmployeeDto getEmployeeDetails(Long userId);
    public String updateEmployee(EmployeeUpdateDto employeeUpdateDto);
    public String deleteEmployee(Long user_id);

}

package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.DTO.*;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @PostMapping("/employee-registration")
    public ResponseEntity<String> employeeRegistration(@RequestBody EmployeeRegisterDto employeeRegisterDto){
        return ResponseEntity.ok(adminService.saveEmployee(employeeRegisterDto));
    }

    @PostMapping("/employee-update")
    public ResponseEntity<String> employeeUpdate(@RequestBody EmployeeUpdateDto employeeRegisterDto){
        return ResponseEntity.ok(adminService.updateEmployee(employeeRegisterDto));
    }

    @PostMapping("/employee-delete")
    public ResponseEntity<String> employeeDelete(@RequestParam Long user_id){
        return ResponseEntity.ok(adminService.deleteEmployee(user_id));
    }


    @GetMapping("/employeeList")
    public ResponseEntity<List<EmployeeList>> getEmployees(){
        return ResponseEntity.ok(adminService.employeeList());
    }

    @GetMapping("/getSingleEmployee")
    public ResponseEntity<SingleEmployeeDto> getSingleEmployee(@RequestParam Long user_id){
        return ResponseEntity.ok(adminService.getEmployeeDetails(user_id));
    }
}

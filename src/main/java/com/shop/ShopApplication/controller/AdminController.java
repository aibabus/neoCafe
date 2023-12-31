package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.adminDTO.AdminLoginDto;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeList;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeRegisterDto;
import com.shop.ShopApplication.dto.employeeDTO.EmployeeUpdateDto;
import com.shop.ShopApplication.dto.employeeDTO.SingleEmployeeDto;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;




    @PostMapping("/employee-registration")
    public ResponseEntity<String> employeeRegistration(@RequestBody EmployeeRegisterDto employeeRegisterDto) throws ParseException {
        return ResponseEntity.ok(adminService.saveEmployee(employeeRegisterDto));
    }

    @PutMapping("/employee-update")
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

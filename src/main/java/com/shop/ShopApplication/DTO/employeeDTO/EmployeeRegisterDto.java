package com.shop.ShopApplication.DTO.employeeDTO;

import com.shop.ShopApplication.DTO.filialDTO.WorkingTimeDto;
import com.shop.ShopApplication.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeRegisterDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDate birthDate;
    private String phoneNumber;
    private Long filial_id;
    private WorkingTimeDto workingTimeDto;
}

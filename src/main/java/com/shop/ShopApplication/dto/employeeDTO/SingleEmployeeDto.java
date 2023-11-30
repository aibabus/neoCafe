package com.shop.ShopApplication.dto.employeeDTO;

import com.shop.ShopApplication.entity.WorkingTime;
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
public class SingleEmployeeDto {
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDate birthDate;
    private String phoneNumber;
    private String filialName;
    private WorkingTime workingTime;
}

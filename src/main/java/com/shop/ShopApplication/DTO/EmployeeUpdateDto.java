package com.shop.ShopApplication.DTO;

import com.shop.ShopApplication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeUpdateDto {
    private Long user_id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;
    private LocalDate birthDate;
    private String phoneNumber;
    private Long filial_id;
}

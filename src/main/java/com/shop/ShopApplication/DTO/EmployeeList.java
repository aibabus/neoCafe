package com.shop.ShopApplication.DTO;

import com.shop.ShopApplication.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeList {
    private Long user_id;
    private String name;
    private Role role;
    private String filial_name;
    private String phoneNumber;
}

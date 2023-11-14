package com.shop.ShopApplication.DTO.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BaristaLoginDto {
    private String login;
    private String password;
}

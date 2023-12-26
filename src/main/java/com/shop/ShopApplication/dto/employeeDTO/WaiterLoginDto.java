package com.shop.ShopApplication.dto.employeeDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaiterLoginDto {
    private String login;
    private String password;
}

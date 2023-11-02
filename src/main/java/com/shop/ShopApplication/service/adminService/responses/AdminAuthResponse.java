package com.shop.ShopApplication.service.adminService.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AdminAuthResponse {
    private String message;
    private String token;
}
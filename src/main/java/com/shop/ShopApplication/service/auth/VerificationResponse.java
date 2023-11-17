package com.shop.ShopApplication.service.auth;

import com.shop.ShopApplication.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VerificationResponse {
    private String message;
    private String token;
    private Boolean isSucceed;
    private User user;
}

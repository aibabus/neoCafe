package com.shop.ShopApplication.service.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendCodeResponse {
    private String message;
    private Boolean isSucceed;
}

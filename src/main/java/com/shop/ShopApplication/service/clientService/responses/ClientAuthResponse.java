package com.shop.ShopApplication.service.clientService.responses;

import com.shop.ShopApplication.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientAuthResponse {
    private String message;
    private String token;
    private Boolean isSucceed;
    private User user;

    public boolean isSucceed() {
        return isSucceed;
    }
}

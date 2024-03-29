package com.shop.ShopApplication.service.smsServices.smsSender;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendSmsResponse {
    private String message;
    private Boolean isSucceed;
    public boolean isSucceed() {
        return isSucceed;
    }
}

package com.shop.ShopApplication.register;

import com.shop.ShopApplication.entity.VerificationCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientLoginDto {
    private String phoneNumber;
    private String verificationCode;
}

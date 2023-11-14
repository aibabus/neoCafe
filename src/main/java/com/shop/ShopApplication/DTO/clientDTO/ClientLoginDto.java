package com.shop.ShopApplication.DTO.clientDTO;

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

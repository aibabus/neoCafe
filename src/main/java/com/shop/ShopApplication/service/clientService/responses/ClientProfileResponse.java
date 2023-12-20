package com.shop.ShopApplication.service.clientService.responses;

import com.shop.ShopApplication.dto.clientDTO.ClientProfileDto;
import com.shop.ShopApplication.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientProfileResponse {
    private String message;
    private String token;
    private Boolean isSucceed;
    private ClientProfileDto clientProfileDto;
}


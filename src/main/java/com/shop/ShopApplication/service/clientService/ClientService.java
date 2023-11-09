package com.shop.ShopApplication.service.clientService;

import com.shop.ShopApplication.DTO.ClientLoginDto;
import com.shop.ShopApplication.DTO.ClientRegisterDto;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;

public interface ClientService {
    public ClientAuthResponse registerClient(ClientRegisterDto request);
    public ClientAuthResponse login(String phoneNumber, String code);
    public String deleteUser(Long user_id);
}

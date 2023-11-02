package com.shop.ShopApplication.service.clientService;

import com.shop.ShopApplication.register.ClientRegisterDto;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;

public interface ClientService {
    public ClientAuthResponse registerClient(ClientRegisterDto request);
    public boolean verifyPhoneNumber(String phoneNumber, String code);
}

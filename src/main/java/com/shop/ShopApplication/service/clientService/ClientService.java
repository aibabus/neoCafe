package com.shop.ShopApplication.service.clientService;

import com.shop.ShopApplication.dto.clientDTO.ClientRegisterDto;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
import com.shop.ShopApplication.service.clientService.responses.ClientResponse;

public interface ClientService {
    public ClientAuthResponse registerClient(ClientRegisterDto request);
    public ClientAuthResponse login(String phoneNumber, String code);
    public ClientResponse deleteUser(Long user_id);
    public String deleteTest(Long user_id);
}

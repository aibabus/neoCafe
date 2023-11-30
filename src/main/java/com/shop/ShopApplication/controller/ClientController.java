package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.clientService.ClientService;
import com.shop.ShopApplication.service.clientService.responses.ClientResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client")
@RequiredArgsConstructor
public class ClientController {
    private final AuthService authService;
    private final SmsService smsService;
    private final ClientService clientService;



    @PostMapping("/client-delete")
    public ResponseEntity<ClientResponse> clientDelete(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteUser(user_id));
    }

    @DeleteMapping("/client-delete-test")
    public ResponseEntity<String> clientDeleteTest(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteTest(user_id));
    }

}

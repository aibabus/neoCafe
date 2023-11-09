package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.DTO.ClientRegisterDto;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.service.auth.VerificationResponse;
import com.shop.ShopApplication.service.clientService.ClientService;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
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

    @PostMapping("/auth/sendCodeRegistration")
    public ResponseEntity<SendCodeResponse> sendRegistrationCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.sendVerificationCodeRegister(phoneNumber));
    }

    @PostMapping("/auth/verify-phone-number")
    public ResponseEntity<VerificationResponse> verifyPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ){
        return ResponseEntity.ok(authService.verifyPhoneNumber(phoneNumber, code));
    }

    @PostMapping("/auth/registration")
    public ResponseEntity<ClientAuthResponse> clientRegistration(@RequestBody ClientRegisterDto request){
        return ResponseEntity.ok(clientService.registerClient(request));
    }

    @PostMapping("/auth/sendCodeLogin")
    public ResponseEntity<SendCodeResponse> sendLoginCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.sendVerificationCodeLogin(phoneNumber));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<ClientAuthResponse> verifyPhoneNumberLogin(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ){
        return ResponseEntity.ok(clientService.login(phoneNumber, code));

    }

    @PostMapping("/client-delete")
    public ResponseEntity<String> clientDelete(@RequestParam Long user_id){
        return ResponseEntity.ok(clientService.deleteUser(user_id));
    }

}

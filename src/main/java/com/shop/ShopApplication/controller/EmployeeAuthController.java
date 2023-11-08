package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth/employee")
@RequiredArgsConstructor
public class EmployeeAuthController {
    private final SmsService smsService;
    private final AuthService authService;

    @PostMapping("/sendCodeWaiter")
    public ResponseEntity<SendCodeResponse> sendLoginCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.sendVerificationCodeWaiter(phoneNumber));
    }

    @PostMapping("/loginVerification")
    public ResponseEntity<AuthResponse> verifyPhoneNumberLogin(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ){
        return ResponseEntity.ok(authService.loginWaiter(phoneNumber, code));

    }

}

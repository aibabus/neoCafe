package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.register.AuthService;
import com.shop.ShopApplication.register.SendCodeResponse;
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

    @PostMapping("/auth/sendCodeRegistration")
    public ResponseEntity<SendCodeResponse> sendRegistrationCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.sendVerificationCodeRegister(phoneNumber));
    }

    @PostMapping("/auth/verify-phone-number")
    public ResponseEntity<String> verifyPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ){
        boolean isVerified = authService.verifyPhoneNumber(phoneNumber, code);
        if (isVerified) {
            return ResponseEntity.ok("Phone number verified successfully.");
        } else {
            return ResponseEntity.badRequest().body("Phone number verification failed.");
        }
    }



}

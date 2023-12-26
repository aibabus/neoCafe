package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.clientDTO.ClientRegisterDto;
import com.shop.ShopApplication.dto.employeeDTO.WaiterLoginDto;
import com.shop.ShopApplication.service.auth.AuthResponse;
import com.shop.ShopApplication.service.auth.AuthService;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.service.auth.VerificationResponse;
import com.shop.ShopApplication.service.clientService.ClientService;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
import com.shop.ShopApplication.service.smsServices.smsSender.SmsService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final SmsService smsService;
    private final ClientService clientService;

    @PostMapping("/client/registration")
    public ResponseEntity<ClientAuthResponse> clientRegistration(@RequestBody ClientRegisterDto request){
        return ResponseEntity.ok(clientService.registerClient(request));
    }

    @PostMapping("/client/sendCodeRegistration")
    public ResponseEntity<SendCodeResponse> sendRegistrationCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.sendVerificationCodeRegister(phoneNumber));
    }

    @PostMapping("/verify-phone-number")
    public ResponseEntity<VerificationResponse> verifyPhoneNumber(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ){
        return ResponseEntity.ok(authService.verifyPhoneNumber(phoneNumber, code));
    }



    @PostMapping("/sendCodeLogin")
    public ResponseEntity<SendCodeResponse> sendLoginCode(@RequestParam String phoneNumber) {
        return ResponseEntity.ok(smsService.sendVerificationCodeLogin(phoneNumber));
    }


    @PostMapping("/login")
    public ResponseEntity<ClientAuthResponse> verifyPhoneNumberLogin(
            @RequestParam String phoneNumber,
            @RequestParam String code
    ){
        return ResponseEntity.ok(clientService.login(phoneNumber, code));

    }

    @Operation(summary = "Отправка смс кода официанту, если логин и пароль верны")
    @PostMapping("/waiter/sendVerificationCodeWaiter")
    public ResponseEntity<SendCodeResponse> sendVerificationCodeWaiter(@RequestBody WaiterLoginDto request){
        SendCodeResponse response = smsService.sendVerificationCodeWaiter(request);
        return new ResponseEntity<>(response, response.getIsSucceed() ? HttpStatus.OK : HttpStatus.BAD_REQUEST);
    }

    @Operation(summary = "Подтверждение кода и выдача токена")
    @PostMapping("/waiter/login")
    public ResponseEntity<AuthResponse> login(@RequestParam String phoneNumber,
                                              @RequestParam String code) {
        return ResponseEntity.ok(authService.loginWaiter(phoneNumber, code));
    }

}

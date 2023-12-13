package com.shop.ShopApplication.service.auth;

import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.jwt.JwtService;

import com.shop.ShopApplication.entity.VerificationCode;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AdminService userService;


    public VerificationResponse verifyPhoneNumber(String phoneNumber, String code) {
        //TODO: change boolean to response message

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber);

        if (verificationCode == null || !verificationCode.getCode().equals(code)) {
            return VerificationResponse.builder()
                    .message("Введеный вами код неверен")
                    .isSucceed(false)
                    .build();
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(verificationCode.getExpirationTime())) {

            return VerificationResponse.builder()
                    .message("Введеный вами код устарел")
                    .isSucceed(false)
                    .build();

        }

        verificationCodeRepository.delete(verificationCode);
        var user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        user.setEnabled(true);
        return VerificationResponse.builder()
                .message("Успешно !")
                .isSucceed(true)
                .token(jwtToken)
                .user(user)
                .build();
    }

    public AuthResponse loginWaiter(String phoneNumber, String code) {

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber);
        if (verificationCode == null || !verificationCode.getCode().equals(code)) {
            return AuthResponse.builder()
                    .message("Введеный вами код неверен")
                    .build();
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(verificationCode.getExpirationTime())) {
            verificationCodeRepository.delete(verificationCode);
            return AuthResponse.builder()
                    .message("Введеный вами код устарел")
                    .build();

        }

        verificationCodeRepository.delete(verificationCode);
        var user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}

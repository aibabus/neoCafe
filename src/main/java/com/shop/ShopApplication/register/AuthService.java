package com.shop.ShopApplication.register;

import com.shop.ShopApplication.config.JwtService;

import com.shop.ShopApplication.entity.VerificationCode;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final VerificationCodeRepository verificationCodeRepository;
    private final AdminService userService;


    public AuthResponse login(ClientLoginDto request) {
        var user = userRepository.findByLogin(request.getPhoneNumber()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }


    public boolean verifyPhoneNumber(String phoneNumber, String code) {
        //TODO: change boolean to response message

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber);

        if (verificationCode == null || !verificationCode.getCode().equals(code)) {
            //TODO: add message
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(verificationCode.getExpirationTime())) {
            //TODO: add message
            return false;
        }

        verificationCode.setPhoneConfirmedAt(now);
        verificationCodeRepository.delete(verificationCode);
        return true;
    }
}

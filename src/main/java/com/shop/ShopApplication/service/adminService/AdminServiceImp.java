package com.shop.ShopApplication.service.adminService;
import com.shop.ShopApplication.DTO.AdminLoginDto;
import com.shop.ShopApplication.config.JwtService;
import com.shop.ShopApplication.entity.Role;
import com.shop.ShopApplication.register.AuthResponse;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.service.adminService.responses.AdminAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImp implements AdminService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationCodeRepository verificationCodeRepository;


    @Override
    public AuthResponse saveAdmin(AdminLoginDto adminLoginDto) {
        var user = User.builder()
                .login(adminLoginDto.getLogin())
                .password(passwordEncoder.encode(adminLoginDto.getPassword()))
                .role(Role.ADMIN)
                .enabled(true)
                .build();
        userRepository.save(user);

        return AuthResponse.builder()
                .message("now you registered")
                .build();
    }

    @Override
    public AdminAuthResponse login(AdminLoginDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                ));
        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AdminAuthResponse.builder()
                .token(jwtToken)
                .build();
    }






//    @Override
//    public UserFullDto getSingleUserByLogin(String login) {
//        Optional<User> user = userRepository.findByLogin(login);
//        if (user.isPresent()){
//            User userEntity = user.get();
//            return new UserFullDto(
//                    userEntity.getUser_id(),
//                    userEntity.getFirstName(),
//                    userEntity.getLastName(),
//                    userEntity.getLogin(),
//                    userEntity.getEmail(),
//                    userEntity.getAvatar(),
//                    userEntity.getPhoneNumber(),
//                    userEntity.getBirthDate()
//            );
//        }
//        throw new RuntimeException("User is not find " + login);
//    }


}

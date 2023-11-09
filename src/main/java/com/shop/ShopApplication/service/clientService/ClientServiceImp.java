package com.shop.ShopApplication.service.clientService;

import com.shop.ShopApplication.JWT.JwtService;
import com.shop.ShopApplication.entity.Role;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.VerificationCode;
import com.shop.ShopApplication.DTO.ClientRegisterDto;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService{
    private final VerificationCodeRepository verificationCodeRepository;
    private final AdminService userService;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Override
    public ClientAuthResponse registerClient(ClientRegisterDto request) {
         Optional<User> existingUser = userRepository.findByPhoneNumber(request.getPhoneNumber());
         if(existingUser.isPresent()){
             return ClientAuthResponse.builder()
                     .message("Пользователь с таким номером телефона уже существует")
                     .build();
         }


        var user = User.builder()
                .firstName(request.getFirstName())
                .phoneNumber(request.getPhoneNumber())
                .birthDate(request.getBirthDate())
                .role(Role.CLIENT)
                .enabled(true)
                .build();
        if (user == null) {
            return ClientAuthResponse.builder()
                    .message("Данные не были сохранены, данные не подходят под формат")
                    .build();
        }

        userRepository.save(user);

        return ClientAuthResponse.builder()
                .message("Вы успешно зарегистрированы!!!")
                .build();
    }





    public ClientAuthResponse login(String phoneNumber, String code) {

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber);

        if (verificationCode == null || !verificationCode.getCode().equals(code)) {
            return ClientAuthResponse.builder()
                    .message("Введеный вами код неверен")
                    .build();
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(verificationCode.getExpirationTime())) {

            return ClientAuthResponse.builder()
                    .message("Введеный вами код устарел")
                    .build();

        }

            verificationCodeRepository.delete(verificationCode);
            var user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return ClientAuthResponse.builder()
                    .token(jwtToken)
                    .build();
        }
        @Override
        public String deleteUser(Long user_id){
        Optional<User> optionalUser = userRepository.findById(user_id);
        if(optionalUser.isEmpty()){
            return "Такого пользователся не существует";
        }
        User user = optionalUser.get();
        user.setEnabled(false);
        return "Пользователь удален ";
        }

}



package com.shop.ShopApplication.service.clientService;

import com.shop.ShopApplication.JWT.JwtService;
import com.shop.ShopApplication.entity.enums.Role;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.VerificationCode;
import com.shop.ShopApplication.DTO.clientDTO.ClientRegisterDto;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
import com.shop.ShopApplication.service.clientService.responses.ClientResponse;
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
                    .isSucceed(false)
                    .build();
        }

        userRepository.save(user);

        return ClientAuthResponse.builder()
                .message("Вы успешно зарегистрированы!!!")
                .isSucceed(true)
                .build();
    }





    public ClientAuthResponse login(String phoneNumber, String code) {

        VerificationCode verificationCode = verificationCodeRepository.findByPhoneNumber(phoneNumber);

        if (verificationCode == null || !verificationCode.getCode().equals(code)) {
            return ClientAuthResponse.builder()
                    .message("Введеный вами код неверен")
                    .isSucceed(false)
                    .build();
        }

        LocalDateTime now = LocalDateTime.now();
        if (now.isAfter(verificationCode.getExpirationTime())) {

            return ClientAuthResponse.builder()
                    .message("Введеный вами код устарел")
                    .isSucceed(false)
                    .build();

        }

            verificationCodeRepository.delete(verificationCode);
            var user = userRepository.findByPhoneNumber(phoneNumber).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            return ClientAuthResponse.builder()
                    .token(jwtToken)
                    .isSucceed(true)
                    .user(user)
                    .build();
        }
        @Override
        public ClientResponse deleteUser(Long user_id){
        Optional<User> optionalUser = userRepository.findById(user_id);
        if(optionalUser.isEmpty()){
            return ClientResponse.builder()
                    .message("Такого пользователся не существует")
                    .isSucceed(false)
                    .build();
        }
        User user = optionalUser.get();
        user.setEnabled(false);
            return ClientResponse.builder()
                    .message("Пользователь удален ")
                    .isSucceed(true)
                    .build();
        }

        @Override
        public String deleteTest(Long user_id){
        Optional<User> optionalUser = userRepository.findById(user_id);
        User user = optionalUser.get();
        userRepository.delete(user);
        return "Юзер удален";
        }

}



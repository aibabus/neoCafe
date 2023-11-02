package com.shop.ShopApplication.service.clientService;

import com.shop.ShopApplication.entity.Role;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.VerificationCode;
import com.shop.ShopApplication.register.ClientRegisterDto;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.service.adminService.AdminService;
import com.shop.ShopApplication.service.clientService.responses.ClientAuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClientServiceImp implements ClientService{
    private final VerificationCodeRepository verificationCodeRepository;
    private final AdminService userService;
    private final UserRepository userRepository;
    @Override
    public ClientAuthResponse registerClient(ClientRegisterDto request) {

        var user = User.builder()
                .firstName(request.getFirsName())
                .phoneNumber(request.getPhoneNumber())
                .role(Role.CLIENT)
                .enabled(true)
                .build();
        if (user == null) {
            return ClientAuthResponse.builder()
                    .message("Данные не были сохранены, данные не подошли")
                    .build();
        }

        userRepository.save(user);

        return ClientAuthResponse.builder()
                .message("Вы успешно зарегистрированы!!!")
                .build();
    }

    @Override
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

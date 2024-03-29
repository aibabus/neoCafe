package com.shop.ShopApplication.service.smsServices.smsSender;

import com.shop.ShopApplication.dto.employeeDTO.WaiterLoginDto;
import com.shop.ShopApplication.entity.enums.Role;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.VerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;



@Service
@RequiredArgsConstructor
public class SmsService {
    private final SmsSender smsSender;
    private final VerificationCodeRepository verificationCodeRepository ;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender, VerificationCodeRepository verificationCodeRepository, UserRepository userRepository, AuthenticationManager authenticationManager) {
        this.smsSender = smsSender;
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public SendCodeResponse sendVerificationCodeLogin(String phoneNumber) {
        String verificationCode = generateVerificationCode();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (userOptional.isEmpty()) {
            return SendCodeResponse.builder()
                    .message("Пользоватеь с таким номером телефона не найден: " + phoneNumber)
                    .build();
        }

        User user = userOptional.get();
        VerificationCode Verificationcode = new VerificationCode();
        Verificationcode.setCode(verificationCode);
        Verificationcode.setPhoneNumber(phoneNumber);
        Verificationcode.setUser(user);
        Verificationcode.setExpirationTime(expirationTime);
        verificationCodeRepository.save(Verificationcode);

        String message = "Ваш код подтверждения: " + verificationCode;
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);

        try{
            smsSender.sendSms(smsRequest);
            return SendCodeResponse.builder()
                    .message("Ваш код подтверждения был успешно отправлен на номер: " + phoneNumber)
                    .isSucceed(true)
                    .build();
        }catch (Exception e){
            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }
    }


    public SendCodeResponse initiatePhoneNumberUpdate(String currentPhoneNumber, String newPhoneNumber) {
        Optional<User> userOptional = userRepository.findByPhoneNumber(currentPhoneNumber);
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);

        if (userOptional.isEmpty()) {
            return SendCodeResponse.builder()
                    .message("Клиент с таким номером телефона не найден: " + currentPhoneNumber)
                    .isSucceed(false)
                    .build();
        }

        User user = userOptional.get();
        String verificationCode = generateVerificationCode();
        VerificationCode Verificationcode = new VerificationCode();
        Verificationcode.setCode(verificationCode);
        Verificationcode.setPhoneNumber(newPhoneNumber);
        Verificationcode.setUser(user);
        Verificationcode.setExpirationTime(expirationTime);
        verificationCodeRepository.save(Verificationcode);

        String message = "Ваш код подтверждения: " + verificationCode;
        SmsRequest smsRequest = new SmsRequest(newPhoneNumber, message);

        try{
            smsSender.sendSms(smsRequest);
            return SendCodeResponse.builder()
                    .message("Ваш код подтверждения был успешно отправлен на номер: " + newPhoneNumber)
                    .isSucceed(true)
                    .build();
        }catch (Exception e){
            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + newPhoneNumber)
                    .isSucceed(false)
                    .build();
        }
    }


    public SendCodeResponse sendVerificationCodeBarista(String phoneNumber) {

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (userOptional.isEmpty()) {
            return SendCodeResponse.builder()
                    .message("Пользователь с таким номером телефона не найден: " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }
        User user = userOptional.get();
        if(!user.isEnabled()){
            return SendCodeResponse.builder()
                    .message("Официант с таким номером телефона уже не работает: " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }
        if (user.getRole() != Role.BARISTA){
            return SendCodeResponse.builder()
                    .message("Данный пользователь не работает официантом " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }

        String verificationCode = generateVerificationCode();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);

        VerificationCode Verificationcode = new VerificationCode();
        Verificationcode.setCode(verificationCode);
        Verificationcode.setPhoneNumber(phoneNumber);
        Verificationcode.setUser(user);
        Verificationcode.setExpirationTime(expirationTime);
        verificationCodeRepository.save(Verificationcode);

        String message = "Ваш код подтверждения: " + verificationCode;
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
        try{
            smsSender.sendSms(smsRequest);
            return SendCodeResponse.builder()
                    .message("Ваш код подтверждения был успешно отправлен на номер: " + phoneNumber)
                    .isSucceed(true)
                    .build();
        }catch (Exception e){

            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }

    }

    public SendCodeResponse sendVerificationCodeWaiter(WaiterLoginDto request){
                authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getLogin(),
                        request.getPassword()
                ));
        String verificationCode = generateVerificationCode();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);

        var user = userRepository.findByLogin(request.getLogin()).orElseThrow();
        String phoneNumber = user.getPhoneNumber();

        VerificationCode Verificationcode = new VerificationCode();
        Verificationcode.setCode(verificationCode);
        Verificationcode.setPhoneNumber(phoneNumber);
        Verificationcode.setExpirationTime(expirationTime);
        verificationCodeRepository.save(Verificationcode);

        String message = "Ваш код подтверждения: " + verificationCode;
        SmsRequest smsRequest = new SmsRequest(phoneNumber, message);

        try {
            System.out.println(smsRequest);
            smsSender.sendSms(smsRequest);
            return SendCodeResponse.builder()
                    .message("Ваш код подтверждения регистрации был успешно отправлен на номер: " + phoneNumber)
                    .isSucceed(true)
                    .build();
        } catch (Exception e) {

            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }
    }


    public SendCodeResponse sendVerificationCodeRegister(String phoneNumber) {
        String verificationCode = generateVerificationCode();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);


            VerificationCode Verificationcode = new VerificationCode();
            Verificationcode.setCode(verificationCode);
            Verificationcode.setPhoneNumber(phoneNumber);
            Verificationcode.setExpirationTime(expirationTime);
            verificationCodeRepository.save(Verificationcode);


            String message = "Ваш код подтверждения регистрации: " + verificationCode;
            SmsRequest smsRequest = new SmsRequest(phoneNumber, message);
        try {
            System.out.println(smsRequest);
            smsSender.sendSms(smsRequest);
            return SendCodeResponse.builder()
                    .message("Ваш код подтверждения регистрации был успешно отправлен на номер: " + phoneNumber)
                    .isSucceed(true)
                    .build();
        } catch (Exception e) {
            System.out.println(e);
            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
                    .isSucceed(false)
                    .build();
        }

    }





    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
        return String.valueOf(code);
    }
}

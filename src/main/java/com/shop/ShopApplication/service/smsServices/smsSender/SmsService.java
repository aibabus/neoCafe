package com.shop.ShopApplication.service.smsServices.smsSender;

import com.shop.ShopApplication.entity.Role;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.repo.UserRepository;
import com.shop.ShopApplication.repo.VerificationCodeRepository;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.VerificationCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;



@Service
public class SmsService {
    private final SmsSender smsSender;
    private final VerificationCodeRepository verificationCodeRepository ;
    private final UserRepository userRepository;

    @Autowired
    public SmsService(@Qualifier("twilio") TwilioSmsSender smsSender, VerificationCodeRepository verificationCodeRepository,UserRepository userRepository) {
        this.smsSender = smsSender;
        this.verificationCodeRepository = verificationCodeRepository;
        this.userRepository = userRepository;
    }

    public SendCodeResponse sendVerificationCodeLogin(String phoneNumber) {
        String verificationCode = generateVerificationCode();
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expirationTime = currentTime.plusMinutes(5);

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (userOptional.isEmpty()) {
            return SendCodeResponse.builder()
                    .message("User not found for phone number: " + phoneNumber)
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
                    .build();
        }catch (Exception e){
            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
                    .build();
        }
    }


    public SendCodeResponse sendVerificationCodeWaiter(String phoneNumber) {

        Optional<User> userOptional = userRepository.findByPhoneNumber(phoneNumber);

        if (userOptional.isEmpty()) {
            return SendCodeResponse.builder()
                    .message("Пользователь с таким номером телефона не найден: " + phoneNumber)
                    .build();
        }
        User user = userOptional.get();
        if(!user.isEnabled()){
            return SendCodeResponse.builder()
                    .message("Официант с таким номером телефона уже не работает: " + phoneNumber)
                    .build();
        }
        if (user.getRole() != Role.WAITER){
            return SendCodeResponse.builder()
                    .message("Данный пользователь не работает официантом " + phoneNumber)
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
                    .build();
        }catch (Exception e){

            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
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
                    .build();
        } catch (IllegalArgumentException e) {

            return SendCodeResponse.builder()
                    .message("Данный номер телефона не действителен: " + phoneNumber)
                    .build();
        }

    }





    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(9000) + 1000; // Generates a random 4-digit code
        return String.valueOf(code);
    }
}

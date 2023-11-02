package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, Integer> {
    VerificationCode findByPhoneNumber(String phoneNumber);
    VerificationCode findByPhoneNumberAndUser(String phoneNumber, User user);
    void deleteByPhoneNumber(String phoneNumber);

}

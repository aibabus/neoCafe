package com.shop.ShopApplication.repo;

import com.shop.ShopApplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    Optional <User> findByPhoneNumber(String phoneNumber);
    Optional<User> findByLoginOrPhoneNumber(String login, String phoneNumber);

    @Query("SELECT u FROM User u WHERE u.role = 'WAITER' OR u.role = 'BARISTA'")
    List<User> findWaitersAndBaristas();

}

package com.shop.ShopApplication.service.waiterService;

import com.shop.ShopApplication.dto.filialDTO.WorkingTimeDto;
import com.shop.ShopApplication.dto.waiterDTO.WaiterProfileDto;
import com.shop.ShopApplication.entity.User;
import com.shop.ShopApplication.entity.WorkingTime;
import com.shop.ShopApplication.repo.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class WaiterServiceImp implements WaiterService{
    private final UserRepository userRepository;

    @Override
    public WaiterProfileDto getUserProfile(String username) {
        Optional<User> userOptional = userRepository.findByLoginOrPhoneNumber(username, username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with identifier: " + username);
        }

        return convertToWaiterProfileDto(userOptional.get());
    }

    private WaiterProfileDto convertToWaiterProfileDto(User user) {
        WorkingTimeDto workingTimeDto = null;
        if (user.getWorkingTime() != null) {
            WorkingTime workingTime = user.getWorkingTime();
            workingTimeDto = WorkingTimeDto.builder()
                    .monday(workingTime.getMonday())
                    .tuesday(workingTime.getTuesday())
                    .wednesday(workingTime.getWednesday())
                    .thursday(workingTime.getThursday())
                    .friday(workingTime.getFriday())
                    .saturday(workingTime.getSaturday())
                    .sunday(workingTime.getSunday())
                    .build();
        }

        String filialName = user.getFilial() != null ? user.getFilial().getName() : null;

        return WaiterProfileDto.builder()
                .user_id(user.getUser_id())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .birthDate(user.getBirthDate())
                .phoneNumber(user.getPhoneNumber())
                .workingTimeDto(workingTimeDto)
                .filialName(filialName)
                .build();
    }
}

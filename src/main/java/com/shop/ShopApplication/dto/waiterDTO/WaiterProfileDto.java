package com.shop.ShopApplication.dto.waiterDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.shop.ShopApplication.dto.filialDTO.WorkingTimeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WaiterProfileDto {
    private Long user_id;
    private String firstName;
    private String lastName;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private String phoneNumber;
    private WorkingTimeDto workingTimeDto;
    private String filialName;
}

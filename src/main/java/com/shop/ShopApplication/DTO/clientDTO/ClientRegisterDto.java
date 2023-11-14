package com.shop.ShopApplication.DTO.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientRegisterDto {
    private String firstName;
    private String phoneNumber;
    private LocalDate birthDate;
}

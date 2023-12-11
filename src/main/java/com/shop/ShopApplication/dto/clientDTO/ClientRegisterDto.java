package com.shop.ShopApplication.dto.clientDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientRegisterDto {
    private String firstName;
    private String phoneNumber;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
}

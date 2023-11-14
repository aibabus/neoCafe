package com.shop.ShopApplication.DTO.clientDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateFilialDto {
    private String name;
    private String address;
    private String mapLink;
    private String phoneNumber;
    private String image;

}

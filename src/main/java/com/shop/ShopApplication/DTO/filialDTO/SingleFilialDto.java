package com.shop.ShopApplication.DTO.filialDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SingleFilialDto {
    private String name;
    private String address;
    private String mapLink;
    private String phoneNumber;
    private List<String> image;
}

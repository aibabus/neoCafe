package com.shop.ShopApplication.dto.filialDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveFilialDto {
    private String name;
    private String address;
    private String mapLink;
    private String phoneNumber;
    private WorkingTimeDto workingTimeDto;
}

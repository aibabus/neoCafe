package com.shop.ShopApplication.DTO;

import com.shop.ShopApplication.entity.WorkingTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveFilialDto {
    private String name;
    private String address;
    private String mapLink;
    private String phoneNumber;
    private MultipartFile image;
}

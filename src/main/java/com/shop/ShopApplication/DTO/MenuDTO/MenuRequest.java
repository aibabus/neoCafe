package com.shop.ShopApplication.DTO.MenuDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuRequest {
    private String name;
    private Long categoryId;
    private Double price;
    private String description;
    private Long filialId;
    private List<CompositionRequest> composition;
}


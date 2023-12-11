package com.shop.ShopApplication.dto.filialDTO;

import com.shop.ShopApplication.entity.Filial;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FilialStatusDto {
    private Long filialId;
    private boolean isOpen;
    private String timeLeft;
    private Filial filialInfo;
    private LocalTime openingTime;
    private LocalTime closingTime;
}

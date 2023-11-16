package com.shop.ShopApplication.service.filialSevice;

import com.shop.ShopApplication.DTO.filialDTO.FilialListDto;
import com.shop.ShopApplication.DTO.filialDTO.SingleFilialDto;
import com.shop.ShopApplication.DTO.filialDTO.WorkingTimeDto;
import com.shop.ShopApplication.entity.WorkingTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilialService {
    public String saveFilial(String name,
                             String address,
                             String mapLink,
                             String phoneNumber,
                             MultipartFile imageFile) throws IOException;
    public List<FilialListDto> allFilial();
    public SingleFilialDto getFilialDetails(Long filialId);
    public String updateFilial(Long filial_id,
                               String name,
                               String address,
                               String mapLink,
                               String phoneNumber,
                               MultipartFile imageFile) throws IOException;
}
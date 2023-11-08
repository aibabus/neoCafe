package com.shop.ShopApplication.service.filialSevice;

import com.shop.ShopApplication.DTO.FilialListDto;
import com.shop.ShopApplication.DTO.SaveFilialDto;
import com.shop.ShopApplication.DTO.SingleFilialDto;
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
}

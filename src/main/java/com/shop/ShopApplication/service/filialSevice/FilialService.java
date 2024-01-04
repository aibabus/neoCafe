package com.shop.ShopApplication.service.filialSevice;

import com.shop.ShopApplication.dto.filialDTO.AddWorkingTimeDto;
import com.shop.ShopApplication.dto.filialDTO.FilialListDto;
import com.shop.ShopApplication.dto.filialDTO.SaveFilialDto;
import com.shop.ShopApplication.dto.filialDTO.SingleFilialDto;
import com.shop.ShopApplication.entity.WorkingTime;
import com.shop.ShopApplication.service.filialSevice.responses.FilialResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FilialService {
    public FilialResponse saveFilial(SaveFilialDto filialRequest) throws IOException;
    public List<FilialListDto> allFilial();
    public SingleFilialDto getFilialDetails(Long filialId);
    public String updateFilial(Long filial_id,
                               String name,
                               String address,
                               String mapLink,
                               String phoneNumber,
                               MultipartFile imageFile) throws IOException;

    public FilialResponse saveFilialImage(Long filialId, MultipartFile imageFile) throws IOException;
    public void deleteWorkingTime(Long workingTime_id);
    public FilialResponse addWorkingTime(AddWorkingTimeDto workingTime);
}

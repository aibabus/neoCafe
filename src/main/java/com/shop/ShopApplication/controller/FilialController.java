package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.DTO.FilialListDto;
import com.shop.ShopApplication.DTO.SaveFilialDto;
import com.shop.ShopApplication.DTO.SingleFilialDto;
import com.shop.ShopApplication.service.auth.SendCodeResponse;
import com.shop.ShopApplication.service.filialSevice.FilialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/filial")
@RequiredArgsConstructor
public class FilialController {
    private final FilialService filialService;
    @PostMapping("/admin/saveFilial")
    public ResponseEntity<String> saveFilial (@RequestParam String name,
                                              @RequestParam String address,
                                              @RequestParam String mapLink,
                                              @RequestParam String phoneNumber,
                                              @RequestPart MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(filialService.saveFilial(name, address, mapLink, phoneNumber, imageFile));
    }
    @GetMapping("/filialList")
    public ResponseEntity<List<FilialListDto>> filialList(){
        return ResponseEntity.ok(filialService.allFilial());
    }

    @GetMapping("/singleFilial")
    public ResponseEntity<SingleFilialDto> singleFilial(@RequestParam Long filial_id){
        return ResponseEntity.ok(filialService.getFilialDetails(filial_id));
    }

}

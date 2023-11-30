package com.shop.ShopApplication.controller;

import com.shop.ShopApplication.dto.filialDTO.FilialListDto;
import com.shop.ShopApplication.dto.filialDTO.SaveFilialDto;
import com.shop.ShopApplication.dto.filialDTO.SingleFilialDto;
import com.shop.ShopApplication.service.filialSevice.FilialService;
import com.shop.ShopApplication.service.filialSevice.responses.FilialResponse;
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
    public ResponseEntity<FilialResponse> saveFilial (@RequestBody SaveFilialDto request) throws IOException {
        return ResponseEntity.ok(filialService.saveFilial(request));
    }

    @PostMapping("admin/saveFilialImage")
    public ResponseEntity<FilialResponse> saveFilialImage(@RequestParam Long filialId, @RequestParam MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(filialService.saveFilialImage(filialId, imageFile));
    }
    @PutMapping("/admin/updateFilial")
    public ResponseEntity<String> updateFilial (@RequestParam Long filial_id, @RequestParam(required = false) String name,
                                                @RequestParam(required = false) String address,
                                                @RequestParam(required = false) String mapLink,
                                                @RequestParam(required = false) String phoneNumber,
                                                @RequestPart(required = false) MultipartFile imageFile) throws IOException {
        return ResponseEntity.ok(filialService.updateFilial(filial_id, name, address, mapLink, phoneNumber, imageFile));
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

package com.shop.ShopApplication.service.filialSevice;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shop.ShopApplication.DTO.FilialUpdateDto;
import com.shop.ShopApplication.DTO.SaveFilialDto;
import com.shop.ShopApplication.DTO.FilialListDto;
import com.shop.ShopApplication.DTO.SingleFilialDto;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.repo.FilialRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FilialServiceImp implements FilialService{
    private final FilialRepository filialRepository;
    private final Cloudinary cloudinary;
    private static final Logger log = LoggerFactory.getLogger(FilialServiceImp.class);

    @Override
    public String saveFilial(String name,
                             String address,
                             String mapLink,
                             String phoneNumber,
                             MultipartFile imageFile) throws IOException {

        File file = convert(imageFile);

        Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        if(!Files.deleteIfExists(file.toPath())){
            throw new IOException("Failed to delete" + file.getName());
        }
        System.out.println(uploadResult);


        Filial filial = Filial.builder()
                .name(name)
                .address(address)
                .mapLink(mapLink)
                .phoneNumber(phoneNumber)
                .image(uploadResult.get("url").toString())
                .build();


        filialRepository.save(filial);
        return "Филиал успешно добавлен";
    }

    @Override
    public String updateFilial(Long filial_id,
                               String name,
                               String address,
                               String mapLink,
                               String phoneNumber,
                               MultipartFile imageFile) throws IOException {
        Optional<Filial> optionalFilial = filialRepository.findById(filial_id);

        if (optionalFilial.isEmpty()) {
            return "Филиал с указанным идентификатором не найден";
        }

        Filial filial = optionalFilial.get();

        if (name != null) {
            filial.setName(name);
        }
        if (address != null) {
            filial.setAddress(address);
        }
        if (mapLink != null) {
            filial.setMapLink(mapLink);
        }
        if (phoneNumber != null) {
            filial.setPhoneNumber(phoneNumber);
        }
        if (imageFile != null) {
            File file = convert(imageFile);
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Failed to delete " + file.getName());
            }
            System.out.println(uploadResult);
            filial.setImage(uploadResult.get("url").toString());
        }


        filialRepository.save(filial);
        return "Информация о филиале успешно обновлена";
    }

    public File convert(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getName()));
        FileOutputStream fo = new FileOutputStream(file);
        fo.write(multipartFile.getBytes());
        fo.close();
        return file;
     }
    @Override
    public List<FilialListDto> allFilial(){
        List<Filial> filials = filialRepository.findAll();
        return filials.stream()
                .map(filial -> new FilialListDto(
                        filial.getFilial_id(),
                        filial.getName(),
                        filial.getAddress(),
                        filial.isOpen()
                )).collect(Collectors.toList());
    }

    @Override
    public SingleFilialDto getFilialDetails(Long filialId) {
        Optional<Filial> optionalFilial = filialRepository.findById(filialId);
        if (optionalFilial.isPresent()) {
            Filial filial = optionalFilial.get();

            return SingleFilialDto.builder()
                    .name(filial.getName())
                    .address(filial.getAddress())
                    .mapLink(filial.getMapLink())
                    .phoneNumber(filial.getPhoneNumber())
                    .image(Collections.singletonList(filial.getImage()))
                    .build();
        } else {
            return null;
        }
    }



}

//    @Override
//    public List<ProductListDto> findAllProducts() {
//        List<Product> products = productRepository.findAll();
//
//        List<ProductListDto> productListDtos = products.stream()
//                .map(product -> new ProductListDto(
//                        product.getProduct_id(),
//                        product.getImage(),
//                        product.getProductName(),
//                        product.getPrice(),
//                        product.getNumberOfLikes() // Assuming you have a getter for the like count
//                ))
//                .collect(Collectors.toList());
//
//        return productListDtos;
//    }

package com.shop.ShopApplication.service.filialSevice;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.shop.ShopApplication.dto.filialDTO.*;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.entity.MenuProduct;
import com.shop.ShopApplication.entity.WorkingTime;
import com.shop.ShopApplication.repo.FilialRepository;
import com.shop.ShopApplication.repo.WorkingTimeRepository;
import com.shop.ShopApplication.service.filialSevice.responses.FilialResponse;
import com.shop.ShopApplication.service.menuService.responses.MenuResponse;
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
    private final WorkingTimeRepository workingTimeRepository;
    private static final Logger log = LoggerFactory.getLogger(FilialServiceImp.class);

    @Override
    public FilialResponse saveFilial(SaveFilialDto filialRequest) throws IOException {
        WorkingTime workingTime = WorkingTime.builder()
                .monday(filialRequest.getWorkingTimeDto().getMonday())
                .tuesday(filialRequest.getWorkingTimeDto().getTuesday())
                .wednesday(filialRequest.getWorkingTimeDto().getWednesday())
                .thursday(filialRequest.getWorkingTimeDto().getThursday())
                .friday(filialRequest.getWorkingTimeDto().getFriday())
                .saturday(filialRequest.getWorkingTimeDto().getSaturday())
                .sunday(filialRequest.getWorkingTimeDto().getSunday())
                .build();
        if (workingTime == null){
            return FilialResponse.builder()
                    .message("Нужно добавить время работы !")
                    .isSucceed(false)
                    .build();
        }

        Filial filial = Filial.builder()
                .name(filialRequest.getName())
                .address(filialRequest.getAddress())
                .mapLink(filialRequest.getMapLink())
                .phoneNumber(filialRequest.getPhoneNumber())
                .workingTime(workingTime)
                .build();

        workingTime.setFilial(filial);
        filialRepository.save(filial);
        return FilialResponse.builder()
                .message("Филиал успешно добавлен")
                .isSucceed(true)
                .filial(filial)
                .build();
    }

    @Override
    public FilialResponse saveFilialImage(Long filialId, MultipartFile imageFile) throws IOException {

        Optional<Filial> optionalFilial = filialRepository.findById(filialId);
        if (optionalFilial.isEmpty()) {
            return FilialResponse.builder()
                    .message("Филиал с указанным ID не найден!")
                    .isSucceed(false)
                    .build();
        }

            Filial filial = optionalFilial.get();
            File file = convert(imageFile);

            // Upload the image to Cloudinary
            Map uploadResult = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());

            // Delete the temporary file
            if (!Files.deleteIfExists(file.toPath())) {
                throw new IOException("Ошибка при удалении " + file.getName());
            }

            // Update the product with the new image URL
            filial.setImage(uploadResult.get("url").toString());
            filialRepository.save(filial);

            return FilialResponse.builder()
                    .message("Изображение успешно добавлено к филиалу!")
                    .isSucceed(true)
                    .build();
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

    @Override
    public void deleteWorkingTime(Long workingTime_id){
        workingTimeRepository.deleteById(workingTime_id);
    }

    @Override
    public FilialResponse addWorkingTime(AddWorkingTimeDto workingTime){
        Optional<Filial> optionalFilial = filialRepository.findById(workingTime.getFilial_id());
        Filial filial = optionalFilial.get();
        WorkingTime workingTime1 = WorkingTime.builder()
                .monday(workingTime.getMonday())
                .tuesday(workingTime.getTuesday())
                .wednesday(workingTime.getWednesday())
                .thursday(workingTime.getThursday())
                .friday(workingTime.getFriday())
                .saturday(workingTime.getSaturday())
                .sunday(workingTime.getSunday())
                .filial(filial)
                .build();
        workingTimeRepository.save(workingTime1);
        return FilialResponse.builder().isSucceed(true).build();
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

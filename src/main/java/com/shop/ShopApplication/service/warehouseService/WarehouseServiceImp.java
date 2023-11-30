package com.shop.ShopApplication.service.warehouseService;

import com.shop.ShopApplication.dto.warehouseDTO.SaveWarehouseItemDto;
import com.shop.ShopApplication.dto.warehouseDTO.WarehouseItemList;
import com.shop.ShopApplication.entity.Filial;
import com.shop.ShopApplication.entity.WarehouseItem;
import com.shop.ShopApplication.entity.enums.StockCategory;
import com.shop.ShopApplication.repo.FilialRepository;
import com.shop.ShopApplication.repo.WarehouseRepository;
import com.shop.ShopApplication.service.warehouseService.responses.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WarehouseServiceImp implements WarehouseService {
    private final FilialRepository filialRepository;
    private final WarehouseRepository warehouseRepository;

    @Override
    public ItemResponse saveItem(SaveWarehouseItemDto warehouseItemDto) {
        Optional<Filial> optionalFilial = filialRepository.findById(warehouseItemDto.getFilial_id());
        if (optionalFilial.isEmpty()) {
            return ItemResponse.builder()
                    .message("Такого филиала н существует")
                    .build();
        }
        Filial filial = optionalFilial.get();
        WarehouseItem item = WarehouseItem.builder()
                .name(warehouseItemDto.getName())
                .stockCategory(warehouseItemDto.getStockCategory())
                .quantity(warehouseItemDto.getQuantity())
                .minimalLimit(warehouseItemDto.getMinimalLimit())
                .unit(warehouseItemDto.getUnit())
                .arrivalDate(warehouseItemDto.getArrivalDate())
                .expirationDate(warehouseItemDto.getExpirationDate())
                .filial(filial)
                .build();

        warehouseRepository.save(item);
        return ItemResponse.builder()
                .message("Продукт успешно доваблен !")
                .build();

    }



        @Override
        public List<WarehouseItemList> allWarehouseItemsSyrie(){
        List<WarehouseItem> items = warehouseRepository.findByStockCategory(StockCategory.SYRIE);
        return items.stream()
                .map(item -> new WarehouseItemList(
                        item.getItem_id(),
                        item.getName(),
                        item.getQuantity(),
                        item.getUnit(),
                        item.getMinimalLimit(),
                        item.getArrivalDate()
                )).collect(Collectors.toList());
    }

    @Override
    public List<WarehouseItemList> allWarehouseItems(){
        List<WarehouseItem> items = warehouseRepository.findByStockCategory(StockCategory.PRODUCT);
        return items.stream()
                .map(item -> new WarehouseItemList(
                        item.getItem_id(),
                        item.getName(),
                        item.getQuantity(),
                        item.getUnit(),
                        item.getMinimalLimit(),
                        item.getArrivalDate()
                )).collect(Collectors.toList());
    }


}

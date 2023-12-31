package com.shop.ShopApplication.service.TableService;

import com.shop.ShopApplication.dto.tableDTO.TableListDto;
import com.shop.ShopApplication.dto.tableDTO.TableOrderDto;
import com.shop.ShopApplication.entity.Order;
import com.shop.ShopApplication.entity.RestaurantTable;
import com.shop.ShopApplication.repo.OrderRepository;
import com.shop.ShopApplication.repo.TableRepository;
import com.shop.ShopApplication.service.TableService.response.TableListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TableServiceImp implements TableService{
    private final TableRepository tableRepository;
    private final OrderRepository orderRepository;

    @Override
    public TableListResponse getAllTables() {
        List<RestaurantTable> tables = tableRepository.findAll();

        if(tables.isEmpty()){
            return TableListResponse.builder()
                    .message("Столов не найдено !")
                    .isSucceed(false)
                    .build();
        }

        List<TableListDto> tableDtos = tables.stream()
                .map(table -> TableListDto.builder()
                        .tableId(table.getTable_id())
                        .isAvailable(table.isAvailable())
                        .build())
                .collect(Collectors.toList());

        return TableListResponse.builder()
                .message("Successfully retrieved tables")
                .isSucceed(true)
                .tablesList(tableDtos)
                .build();
    }
    @Override
    public List<TableOrderDto> getAllTablesWithLastOrders() {
        List<RestaurantTable> tables = tableRepository.findAll();

        return tables.stream().map(table -> {
            Optional<Order> lastOrderOpt = orderRepository.findLatestOrderByTableId(table.getTable_id());

            return lastOrderOpt
                    .map(order -> mapToDto(table, order))
                    .orElseGet(() -> mapToDtoWithNoOrder(table));
        }).collect(Collectors.toList());
    }

    private TableOrderDto mapToDto(RestaurantTable table, Order order) {
        return TableOrderDto.builder()
                .tableId(table.getTable_id())
                .orderId(order.getOrderId())
                .orderStatus(order.getOrderStatus())
                .orderTime(order.getOrderDate())
                .build();
    }
    private TableOrderDto mapToDtoWithNoOrder(RestaurantTable table) {
        return TableOrderDto.builder()
                .tableId(table.getTable_id())
                .orderStatus(null)
                .orderId(null)
                .orderTime(null)
                .build();
    }

}

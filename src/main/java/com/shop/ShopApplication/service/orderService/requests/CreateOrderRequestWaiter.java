package com.shop.ShopApplication.service.orderService.requests;

import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequestWaiter {
    private String username;
    private Long tableId;
    private Long filialId;
    private List<OrderItemDto> orderItems;
}

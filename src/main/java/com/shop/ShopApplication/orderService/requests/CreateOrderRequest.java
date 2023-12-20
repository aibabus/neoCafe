package com.shop.ShopApplication.orderService.requests;

import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    private Long userId;
    private List<OrderItemDto> orderItems;

}

package com.shop.ShopApplication.service.orderService.requests;

import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateOrderRequest {
    private Long userId;
    private double minusBonus;
    private Long filial_id;
    private List<OrderItemDto> orderItems;
    private boolean isInside;

}

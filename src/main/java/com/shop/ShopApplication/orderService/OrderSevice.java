package com.shop.ShopApplication.orderService;

import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import com.shop.ShopApplication.entity.Order;
import com.shop.ShopApplication.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


public interface OrderSevice{
    public Order createOrder(Long user_id, List<OrderItemDto> orderItems);
}

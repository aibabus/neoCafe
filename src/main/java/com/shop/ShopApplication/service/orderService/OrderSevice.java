package com.shop.ShopApplication.service.orderService;

import com.shop.ShopApplication.dto.orderDTO.OrderInfoDto;
import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import com.shop.ShopApplication.entity.Order;
import com.shop.ShopApplication.service.orderService.response.OrderResponse;

import java.util.List;


public interface OrderSevice{
    public OrderResponse createOrder(Long user_id, Long filialId, Double minusBonus, List<OrderItemDto> orderItems);
    public List<OrderInfoDto> getAllUserOrders(Long userId);
}

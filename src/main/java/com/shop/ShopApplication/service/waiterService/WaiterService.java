package com.shop.ShopApplication.service.waiterService;

import com.shop.ShopApplication.dto.waiterDTO.WaiterProfileDto;

public interface WaiterService {

    public WaiterProfileDto getUserProfile(String username);
}

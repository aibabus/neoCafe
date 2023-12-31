package com.shop.ShopApplication.dto.tableDTO;

import com.shop.ShopApplication.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TableOrderDto {
    private Long tableId;
    private OrderStatus orderStatus;
    private Long orderId;
    private Date orderTime;
}

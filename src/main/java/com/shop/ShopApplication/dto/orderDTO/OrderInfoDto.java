package com.shop.ShopApplication.dto.orderDTO;

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
public class OrderInfoDto {
    private Long order_id;
    private String menuProductName;
    private String filialImage;
    private String FilialName;
    private boolean isReady;
    private OrderStatus orderStatus;
    private Date orderDate;

}

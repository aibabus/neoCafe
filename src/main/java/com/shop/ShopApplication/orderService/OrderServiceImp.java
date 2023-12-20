package com.shop.ShopApplication.orderService;

import com.shop.ShopApplication.dto.MenuDTO.DopingDto;
import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import com.shop.ShopApplication.entity.*;
import com.shop.ShopApplication.repo.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImp implements OrderSevice {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final DopingRepository dopingRepository;
    private final MenuRepository menuRepository;
    private final UserRepository userRepository;

    @Override
    public Order createOrder(Long user_id, List<OrderItemDto> orderItems) {
        Optional<User> optUser = userRepository.findById(user_id);

        if(optUser.isEmpty()){
            return null;
        }
        User user = optUser.get();

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(new Date());
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (OrderItemDto item : orderItems) {
            OrderDetail detail = new OrderDetail();
            MenuProduct product = menuRepository.findById(item.getMenuProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            detail.setMenuProduct(product);
            detail.setOrder(order);

            List<Doping> dopings = item.getDopingIds().stream()
                    .map(dopingId -> dopingRepository.findById(dopingId)
                            .orElseThrow(() -> new RuntimeException("Doping not found")))
                    .collect(Collectors.toList());
            detail.setDopings(dopings);

            orderDetails.add(detail);
        }

        order.setOrderDetails(orderDetails);
        return orderRepository.save(order);
    }
}

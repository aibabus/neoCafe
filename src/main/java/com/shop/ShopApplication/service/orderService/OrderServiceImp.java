package com.shop.ShopApplication.service.orderService;

import com.shop.ShopApplication.dto.orderDTO.OrderInfoDto;
import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import com.shop.ShopApplication.entity.*;
import com.shop.ShopApplication.entity.enums.OrderStatus;
import com.shop.ShopApplication.repo.*;
import com.shop.ShopApplication.service.orderService.response.OrderResponse;
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
    private final FilialRepository filialRepository;

    @Override
    public OrderResponse createOrder(Long user_id, Long filialId, Double minusBonus, List<OrderItemDto> orderItems) {
        Optional<User> optUser = userRepository.findById(user_id);
        Optional<Filial> optFilial = filialRepository.findById(filialId);

        if(optUser.isEmpty()){
            return OrderResponse.builder()
                    .message("Клиент не найден")
                    .isSucceed(false)
                    .build();
        }
        if (optFilial.isEmpty()) {
            return OrderResponse.builder()
                    .message("Филиал не найден")
                    .isSucceed(false)
                    .build();
        }

        User user = optUser.get();
        Filial filial = optFilial.get();

        Order order = new Order();
        order.setUser(user);
        order.setFilial(filial);
        order.setOrderDate(new Date());

        List<OrderDetail> orderDetails = new ArrayList<>();
        double totalPrice = 0.0;

        for (OrderItemDto item : orderItems) {
            OrderDetail detail = new OrderDetail();
            MenuProduct product = menuRepository.findById(item.getMenuProductId())
                    .orElseThrow(() -> new RuntimeException("Такого продукта в меню не найдено !"));


            double productTotalPrice = product.getPrice() * item.getQuantity();
            totalPrice += productTotalPrice;
            detail.setPrice(productTotalPrice);
            detail.setMenuProduct(product);
            detail.setOrder(order);
            detail.setQuantity(item.getQuantity());


            for (Long dopingId : item.getDopingIds()) {
                Doping doping = dopingRepository.findById(dopingId)
                        .orElseThrow(() -> new RuntimeException("Такого допинга для продукта не найдено !"));
                totalPrice += doping.getPrice();
            }


            List<Doping> dopings = item.getDopingIds().stream()
                    .map(dopingId -> dopingRepository.findById(dopingId)
                            .orElseThrow(() -> new RuntimeException("Такого допинга для продукта не найдено !")))
                    .collect(Collectors.toList());
            detail.setDopings(dopings);

            orderDetails.add(detail);
        }

        if (minusBonus != null && minusBonus > 0) {
            totalPrice -= minusBonus;
            Double bonusLeft = user.getBonus() - minusBonus;
            user.setBonus(bonusLeft);
        }


        order.setReady(false);
        order.setOrderStatus(OrderStatus.NEW);
        totalPrice = Math.max(totalPrice, 0.0);
        order.setPrice(totalPrice);
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);
        userRepository.save(user);

        return OrderResponse.builder()
                .message("Заказ успешно отправлен !")
                .isSucceed(true)
                .finalPrice(totalPrice)
                .order(order)
                .build();
    }

    @Override
    public List<OrderInfoDto> getAllUserOrders(Long userId) {
        List<Order> userOrders = orderRepository.findByUserId(userId);

        return userOrders.stream().map(this::mapToOrderInfoDto).collect(Collectors.toList());
    }

    private OrderInfoDto mapToOrderInfoDto(Order order) {
        OrderInfoDto orderInfoDto = new OrderInfoDto();
        orderInfoDto.setMenuProductName(order.getOrderDetails().stream()
                .map(detail -> detail.getMenuProduct().getName())
                .collect(Collectors.joining(", ")));
        orderInfoDto.setOrderStatus(order.getOrderStatus());
        orderInfoDto.setFilialName(order.getFilial().getName());
        orderInfoDto.setFilialImage(order.getFilial().getImage());
        orderInfoDto.setOrderDate(order.getOrderDate());
        orderInfoDto.setReady(order.isReady());

        return orderInfoDto;
    }

}

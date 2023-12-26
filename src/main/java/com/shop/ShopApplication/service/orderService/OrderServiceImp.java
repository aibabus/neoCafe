package com.shop.ShopApplication.service.orderService;

import com.shop.ShopApplication.dto.orderDTO.OrderDetailDto;
import com.shop.ShopApplication.dto.orderDTO.OrderInfoDto;
import com.shop.ShopApplication.dto.orderDTO.OrderItemDto;
import com.shop.ShopApplication.dto.orderDTO.SingleOrderInfoDto;
import com.shop.ShopApplication.entity.*;
import com.shop.ShopApplication.entity.enums.OrderStatus;
import com.shop.ShopApplication.repo.*;
import com.shop.ShopApplication.service.orderService.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
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
    private final TableRepository tableRepository;

    @Override
    public OrderResponse createOrder(Long user_id, Long filialId, Double minusBonus, List<OrderItemDto> orderItems) {
        Optional<User> optUser = userRepository.findById(user_id);
        Optional<Filial> optFilial = filialRepository.findById(filialId);

        if (optUser.isEmpty()) {
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
        order.setMinusBonus(minusBonus);

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


            if (item.getDopingIds() != null && !item.getDopingIds().isEmpty()) {
                List<Doping> dopings = item.getDopingIds().stream()
                        .map(dopingId -> dopingRepository.findById(dopingId)
                                .orElseThrow(() -> new RuntimeException("Такого допинга для продукта не найдено !")))
                        .collect(Collectors.toList());
                for (Doping doping : dopings) {
                    totalPrice += doping.getPrice();
                }
                detail.setDopings(dopings);
            }
        }

           totalPrice = applyMinusBonus(user, totalPrice, minusBonus);


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
    public OrderResponse createOrderWaiter(String username, Long filialId, Long tableId, List<OrderItemDto> orderItems) {
        Optional<User> userOptional = userRepository.findByLoginOrPhoneNumber(username, username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with identifier: " + username);
        }

        Optional<Filial> optFilial = filialRepository.findById(filialId);
        if (optFilial.isEmpty()) {
            return OrderResponse.builder()
                    .message("Филиал не найден")
                    .isSucceed(false)
                    .build();
        }
        RestaurantTable table = tableRepository.findById(tableId)
                .orElseThrow(() -> new RuntimeException("Стол не найден!"));


        User user = userOptional.get();
        Filial filial = optFilial.get();

        Order order = new Order();

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


            if (item.getDopingIds() != null && !item.getDopingIds().isEmpty()) {
                List<Doping> dopings = item.getDopingIds().stream()
                        .map(dopingId -> dopingRepository.findById(dopingId)
                                .orElseThrow(() -> new RuntimeException("Такого допинга для продукта не найдено !")))
                        .collect(Collectors.toList());
                for (Doping doping : dopings) {
                    totalPrice += doping.getPrice();
                }
                detail.setDopings(dopings);
            }
        }
        order.setTable(table);
        order.setUser(user);
        order.setFilial(filial);
        order.setOrderDate(new Date());
        order.setReady(false);
        order.setOrderStatus(OrderStatus.NEW);
        totalPrice = Math.max(totalPrice, 0.0);
        order.setPrice(totalPrice);
        order.setOrderDetails(orderDetails);
        orderRepository.save(order);

        userRepository.save(user);

        table.setAvailable(false);
        tableRepository.save(table);

        return OrderResponse.builder()
                .message("Заказ успешно добавлен!")
                .isSucceed(true)
                .finalPrice(totalPrice)
                .order(order)
                .build();
    }

    private double applyMinusBonus(User user, double totalPrice, Double minusBonus) {
        if (minusBonus != null && minusBonus > 0) {
            if (user.getBonus() >= minusBonus) {
                totalPrice -= minusBonus;
                user.setBonus(user.getBonus() - minusBonus);
            } else {
                // User doesn't have enough bonus - apply only the available bonus
                totalPrice -= user.getBonus(); // Reduce total price by the amount of available bonus
                user.setBonus(0.0); // All available bonuses are used
            }
        }
        return Math.max(totalPrice, 0.0);
    }

    @Override
    public List<OrderInfoDto> getAllUserOrders(Long userId) {
        List<Order> userOrders = orderRepository.findByUserId(userId);

        return userOrders.stream()
                .sorted(Comparator.comparing(Order::getOrderDate).reversed())
                .map(this::mapToOrderInfoDto)
                .collect(Collectors.toList());
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
    @Override
    public OrderResponse cancelOrder(Long order_id){
        Optional<Order> optionalOrder = orderRepository.findById(order_id);

        if (optionalOrder.isEmpty()){
            return OrderResponse.builder()
                    .message("Заказ не найден")
                    .isSucceed(false)
                    .build();
        }

        Order order = optionalOrder.get();

        switch (order.getOrderStatus()) {
            case NEW -> {
                order.setOrderStatus(OrderStatus.CANCELED);
                orderRepository.save(order);
                return OrderResponse.builder()
                        .message("Заказ отменен !")
                        .isSucceed(true)
                        .build();
            }
            case INPROGRESS -> {
                return OrderResponse.builder()
                        .message("Заказ уже в процессе, его нельзя отменить!")
                        .isSucceed(true)
                        .build();
            }
            case DONE -> {
                return OrderResponse.builder()
                        .message("Заказ уже выполнен, его нельзя отменить!")
                        .isSucceed(true)
                        .build();
            }
            case CLOSED -> {
                return OrderResponse.builder()
                        .message("Заказ уже закрыт, его нельзя отменить!")
                        .isSucceed(true)
                        .build();
            }
            case CANCELED -> {
                return OrderResponse.builder()
                        .message("Заказ уже был ранее отменен!")
                        .isSucceed(true)
                        .build();
            }
            default -> {
                return OrderResponse.builder()
                        .message("Неизвестный статус заказа, отмена невозможна!")
                        .isSucceed(false)
                        .build();
            }
        }
    }

    @Override
    public SingleOrderInfoDto getOrderInfo(Long order_id) {
        Order order = orderRepository.findById(order_id)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        // Check if the order belongs to the user (optional)
        // You may add logic here to verify that the order belongs to the currently logged-in user, if required.

        // Map the order and its details to DTOs
        SingleOrderInfoDto singleOrderInfoDto = SingleOrderInfoDto.builder()
                .order_id(order.getOrderId())
                .filialName(order.getFilial().getName())
                .orderDate(order.getOrderDate())
                .menuProducts(mapOrderDetailsToDto(order.getOrderDetails()))
                .minusBonus(order.getMinusBonus())
                .totalPrice(order.getPrice())
                .build();

        return singleOrderInfoDto;
    }

    private List<OrderDetailDto> mapOrderDetailsToDto(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(detail -> {

            MenuProduct product = detail.getMenuProduct();
            Categories category = product.getCategories();
            double totalPrice = detail.getPrice() * detail.getQuantity();

            return OrderDetailDto.builder()
                    .menuProductName(product.getName())
                    .menuProductPrice(product.getPrice())
                    .menuProductCategoryName(category.getName())
                    .quantity(detail.getQuantity())
                    .finalPrice(totalPrice)
                    .image(product.getImage())
                    .build();
        }).collect(Collectors.toList());
    }

}

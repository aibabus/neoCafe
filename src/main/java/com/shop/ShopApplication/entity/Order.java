package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shop.ShopApplication.entity.enums.OrderStatus;
import com.shop.ShopApplication.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders") // 'order' is often a reserved keyword in SQL
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;
    private double price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private boolean isReady;
    private double minusBonus;

    @ManyToOne
    @JoinColumn(name = "user_id") // Assuming an Order is linked to a User
    private User user;

    @Column(name = "order_date")
    private Date orderDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

    @ManyToOne
    @JoinColumn(name = "filial_id")
    @JsonBackReference
    private Filial filial;


}

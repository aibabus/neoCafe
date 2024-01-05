package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;
@EqualsAndHashCode(callSuper = false, exclude = {"users", "workingTime", "menuProducts", "orders", "tables"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "filial")
public class Filial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long filial_id;
    private String name;
    private String address;
    private String mapLink;
    private String phoneNumber;
    private String image;
    private boolean isOpen;

    @JsonManagedReference
    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    @JsonManagedReference
    @OneToOne(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WorkingTime workingTime;

    @JsonManagedReference
    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuProduct> menuProducts;

    @JsonManagedReference
    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;

    @JsonManagedReference
    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RestaurantTable> tables;

}

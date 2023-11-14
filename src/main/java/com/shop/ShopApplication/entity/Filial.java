package com.shop.ShopApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    @OneToOne(mappedBy = "filial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private WorkingTime workingTime;

}

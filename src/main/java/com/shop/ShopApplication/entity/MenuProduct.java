package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "menu")
public class MenuProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;
    private String name;
    private Double price;
    private String description;
    private String image;
    private boolean hasAdditional;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Categories categories;


    @OneToMany(mappedBy = "menuProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Composition> compositions;

    @OneToMany(mappedBy = "menuProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Doping> dopings;

    @ManyToOne
    @JoinColumn(name = "filial_id")
    @JsonBackReference
    private Filial filial;


}


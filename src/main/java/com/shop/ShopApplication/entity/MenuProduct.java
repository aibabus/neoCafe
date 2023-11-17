package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

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
    private Categories categories;


    @OneToMany(mappedBy = "menuProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Composition> compositions;

    @ManyToOne
    @JoinColumn(name = "filial_id")
    private Filial filial;


}


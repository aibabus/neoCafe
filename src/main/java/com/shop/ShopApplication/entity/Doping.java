package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shop.ShopApplication.entity.enums.Unit;
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
@Table(name = "doping")
public class Doping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long doping_id;
    private Double quantity;
    private Unit unit;
    private Double price;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private MenuProduct menuProduct;

    @ManyToMany
    @JoinTable(
            name = "doping_item",
            joinColumns = @JoinColumn(name = "doping_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<WarehouseItem> items;

}

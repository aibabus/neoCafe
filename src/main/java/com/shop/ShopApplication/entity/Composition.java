package com.shop.ShopApplication.entity;

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
@Table(name = "composition")
public class Composition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long composition_id;
    private Double quantity;
    private Unit unit;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private MenuProduct menuProduct;

    @ManyToMany
    @JoinTable(
            name = "composition_item",
            joinColumns = @JoinColumn(name = "composition_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<WarehouseItem> items;

}


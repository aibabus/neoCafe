package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.shop.ShopApplication.entity.enums.StockCategory;
import com.shop.ShopApplication.entity.enums.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.DependsOn;

import javax.persistence.*;
import java.sql.Date;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "warehouse")
public class WarehouseItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long item_id;

    private String name;

    @Enumerated(EnumType.STRING)
    private StockCategory stockCategory;

    private Double quantity;

    @Column(name = "minimal_limit")
    private Double minimalLimit;

    @Enumerated(EnumType.STRING)
    private Unit unit;

    @Column(name = "arrival_date")
    private Date arrivalDate;

    @Column(name = "expiration_date")
    private Date expirationDate;

    @ManyToOne
    @JoinColumn(name = "filial_id")
    @JsonBackReference
    private Filial filial;

}

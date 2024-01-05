package com.shop.ShopApplication.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
@EqualsAndHashCode(callSuper = false, exclude = {"user", "filial"})
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workingTime")
public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long workingTime_id;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

    @OneToOne
    @JoinColumn(name = "filial_id")
    @JsonBackReference
    private Filial filial;

}

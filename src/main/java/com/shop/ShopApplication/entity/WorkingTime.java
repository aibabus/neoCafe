package com.shop.ShopApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "workingTime")
public class WorkingTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long workingTime_id;
    private Date monday;
    private Date tuesday;
    private Date wednesday;
    private Date thursday;
    private Date friday;
    private Date saturday;
    private Date sunday;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "filial_id")
    private Filial filial;

}

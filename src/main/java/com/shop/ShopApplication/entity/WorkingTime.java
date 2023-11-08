package com.shop.ShopApplication.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Time;
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
    private Long workingTime_id;
    private Time monday;
    private Time tuesday;
    private Time wednesday;
    private Time thursday;
    private Time friday;
    private Time saturday;
    private Time sunday;

//    @OneToOne
//    @JoinColumn(name = "user_id")
//    private User user;

//    @OneToOne
//    @JoinColumn(name = "filial_id")
//    private Filial filial;

}

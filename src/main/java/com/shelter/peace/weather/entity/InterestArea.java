package com.shelter.peace.weather.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "INTEREST_AREA")
@Getter
@Setter
public class InterestArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AREA_NO") //
    private Long id;

    private String area1Name;
    private double area1Lat;
    private double area1Lon;

    private String area2Name;
    private double area2Lat;
    private double area2Lon;

    // 유저 조인
    private Long userPk;
}

package com.shelter.peace.weather.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "KOREA_AREA")
@Getter
@Setter
public class KoreaArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "KR_AREA_NO") //
    private Long id;
    String sido;
    String sigungu;
    String uupmyundong;
    String uupmyunleedong;
    String lee;
    double updo;
    double gyungdo;



}

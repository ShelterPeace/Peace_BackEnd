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
    private String areaNo1;
    private String areaNo1Name;
    private String areaNo2;
    private String areaNo2Name;

    // 유저 조인
    private Long userPk;
}

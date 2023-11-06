package com.shelter.peace.aedApi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class AedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    // 위도
    @Column(nullable = true)
    private double wgs84Lat;
    // 경도
    @Column(nullable = true)
    private double wgs84Lon;
    // 우편번호(앞자리)
    @Column(nullable = true)
    private String zipcode1;
    // 우편번호(뒷자리)
    @Column(nullable = true)
    private String zipcode2;
    // 설치기관명
    @Column(nullable = true)
    private String org;
    // 관리책임자명
    @Column(nullable = true)
    private String manager;
    // 관리자 연락처
    @Column(nullable = true)
    private String managerTel;
    // 제조사
    @Column(nullable = true)
    private String mfg;
    // AED 모델명
    @Column(nullable = true)
    private String model;
    // 설치기관주소
    @Column(nullable = true)
    private String buildAddress;
    // 설치위치
    @Column(nullable = true)
    private String buildPlace;
    // 설치기관전화번호
    @Column(nullable = true)
    private String clerkTel;

    @Column(nullable = false)
    private String uniqueKey; // 고유 키(중복방지)
}

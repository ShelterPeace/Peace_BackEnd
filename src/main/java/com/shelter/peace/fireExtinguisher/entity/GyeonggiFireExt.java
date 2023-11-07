package com.shelter.peace.fireExtinguisher.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class GyeonggiFireExt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String sigunNm; //시군구명

    @Column(nullable = true)
    private String facLtDeviceDetailDivNm; //상세위치

    @Column(nullable = true)
    private double refineWgs84Lat; //위도

    @Column(nullable = true)
    private double refineWgs84Logt; //경도

    @Column(nullable = true)
    private String jurisdFiresttnNm; //관할기관명

    @Column(nullable = true)
    private String jurisdFiresttnTelNo; // 관할기관전화번호

    @Column(nullable = true)
    private int facLtTypeCd; //시설유형코드

    @Column(nullable = true)
    private String usePosblYn; //사용가능여부

    @Column(nullable = true)
    private String sigunCd; //시군구코드

    @Column(nullable = true)
    private String sidoNm; //시도명

    @Column(nullable = true)
    private String facLtNo; //시설번호

    @Column(nullable = true)
    private String refineLotnoAddr; //소재지지번주소

    @Column(nullable = true)
    private String refineRoadNmAddr; //소재지도로명주소

    @Column(nullable = true)
    private String instNm; //안전센터명


}

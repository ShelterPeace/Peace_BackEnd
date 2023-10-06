package com.shelter.peace.shelterApi.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Data
public class CivilShelterData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String 번호;
    private String 개방서비스명;
    private String 개방서비스아이디;
    private String 개방자치단체코드;
    @Column(unique = true)
    private String 관리번호;
    private String 인허가일자;
    private String 영업상태구분코드;
    private String 영업상태명;
    private String 상세영업상태코드;
    private String 상세영업상태명;
    private String 소재지면적;
    private String 소재지전체주소;
    private String 도로명전체주소;
    private String 도로명우편번호;
    private String 사업장명;
    private String 최종수정시점;
    private String 데이터갱신구분;
    private String 데이터갱신일자;
    private String 좌표정보_x;
    private String 좌표정보_y;
    private String 비상시설위치;
    private String 시설구분명;
    private String 시설명건물명;

}

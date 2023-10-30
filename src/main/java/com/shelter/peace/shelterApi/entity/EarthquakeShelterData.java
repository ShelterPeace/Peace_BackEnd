package com.shelter.peace.shelterApi.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class EarthquakeShelterData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String arcd;                  // 지진 대피장소 코드

    private String acmdfclty_sn;          // 대피시설 일련번호
    private String ctprvn_nm;             // 시도명
    private String sgg_nm;                // 시군구명
    private String vt_acmdfclty_nm;       // 대피장소명
    private String rdnmadr_cd;            // 소재지 도로명코드
    private String bdong_cd;              // 소재지 법정동 코드
    private String hdong_cd;              // 소재지 행정동 코드
    private String dtl_adres;             // 상세 주소
    private String fclty_ar;              // 시설면적
    private String xcord;                 // X 좌표 (경도)
    private String ycord;                 // Y 좌표 (위도)

    public String getAcmdfclty_sn() {
        return acmdfclty_sn;
    }

}



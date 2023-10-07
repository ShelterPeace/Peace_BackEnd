package com.shelter.peace.shelterApi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EarthquakeShelterDTO {
    @JsonProperty("arcd")
    private String arcd;                  // 지진 대피장소 코드

    @JsonProperty("acmdfclty_sn")
    private String acmdfclty_sn;          // 대피시설 일련번호

    @JsonProperty("ctprvn_nm")
    private String ctprvn_nm;             // 시도명

    @JsonProperty("sgg_nm")
    private String sgg_nm;                // 시군구명

    @JsonProperty("vt_acmdfclty_nm")
    private String vt_acmdfclty_nm;       // 대피장소명

    @JsonProperty("rdnmadr_cd")
    private String rdnmadr_cd;            // 소재지 도로명코드

    @JsonProperty("bdong_cd")
    private String bdong_cd;              // 소재지 법정동 코드

    @JsonProperty("hdong_cd")
    private String hdong_cd;              // 소재지 행정동 코드

    @JsonProperty("dtl_adres")
    private String dtl_adres;             // 상세 주소

    @JsonProperty("fclty_ar")
    private String fclty_ar;              // 시설면적

    @JsonProperty("xcord")
    private String xcord;                 // X 좌표 (경도), 숫자 타입으로 변경

    @JsonProperty("ycord")
    private String ycord;                 // Y 좌표 (위도), 숫자 타입으로 변경




    // EarthquakeShelterResponseDTO 클래스
    public class EarthquakeShelterResponseDTO {
        private List<EarthquakeShelterDTO> earthquakeOutdoorsShelter;

    }
}
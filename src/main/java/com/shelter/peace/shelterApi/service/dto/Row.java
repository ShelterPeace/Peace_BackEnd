package com.shelter.peace.shelterApi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Row {
    @JsonProperty("arcd")
    private String arcd;

    @JsonProperty("acmdfclty_sn")
    private String acmdfcltySn;

    @JsonProperty("ctprvn_nm")
    private String ctprvnNm;

    @JsonProperty("sgg_nm")
    private String sggNm;

    @JsonProperty("vt_acmdfclty_nm")
    private String vtAcmdfcltyNm;

    @JsonProperty("rdnmadr_cd")
    private String rdnmadrCd;

    @JsonProperty("bdong_cd")
    private String bdongCd;

    @JsonProperty("hdong_cd")
    private String hdongCd;

    @JsonProperty("dtl_adres")
    private String dtlAdres;

    // 'fclty_ar'는 소수점을 포함하는 수치이므로 double 타입으로 선언합니다.

    @JsonProperty("fclty_ar")
    double fcltyAr;

    // "xcord"와 "ycord" 필드는 실수형 값이므로 double 타입으로 선언합니다.

    @JsonProperty("xcord")
    double xcord;

    @JsonProperty("ycord")
    double ycord;
}

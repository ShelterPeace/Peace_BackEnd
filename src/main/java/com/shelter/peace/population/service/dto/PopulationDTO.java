package com.shelter.peace.population.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PopulationDTO {
    @JsonProperty("AREA_NM")
    private String AREA_NM;             // 핫스팟 장소명

    @JsonProperty("AREA_CD")
    private String AREA_CD;             // 핫스팟 코드명

    @JsonProperty("LIVE_PPLTN_STTS")
    private String LIVE_PPLTN_STTS;     // 실시간 인구현황

    @JsonProperty("AREA_CONGEST_LVL")
    private String AREA_CONGEST_LVL;    // 장소 혼잡도 지표

    @JsonProperty("AREA_CONGEST_MSG")
    private String AREA_CONGEST_MSG;    // 장소 혼잡도 지표 관련 메세지

    @JsonProperty("AREA_PPLTN_MIN")
    private int AREA_PPLTN_MIN;      // 실시간 인구 지표 최소값

    @JsonProperty("AREA_PPLTN_MAX")
    private int AREA_PPLTN_MAX;      // 실시간 인구 지표 최대값

    @JsonProperty("MALE_PPLTN_RATE")
    private double MALE_PPLTN_RATE;     // 남성 인구 비율(남성)

    @JsonProperty("FEMALE_PPLTN_RATE")
    private double FEMALE_PPLTN_RATE;   // 여성 인구 비율(여성)

    @JsonProperty("PPLTN_RATE_0")
    private double PPLTN_RATE_0;        // 0~10세 인구 비율

    @JsonProperty("PPLTN_RATE_10")
    private double PPLTN_RATE_10;       // 10대 실시간 인구 비율

    @JsonProperty("PPLTN_RATE_20")
    private double PPLTN_RATE_20;       // 20대 실시간 인구 비율

    @JsonProperty("PPLTN_RATE_30")
    private double PPLTN_RATE_30;       // 30대 실시간 인구 비율

    @JsonProperty("PPLTN_RATE_40")
    private double PPLTN_RATE_40;       // 40대 실시간 인구 비율

    @JsonProperty("PPLTN_RATE_50")
    private double PPLTN_RATE_50;       // 50대 실시간 인구 비율

    @JsonProperty("PPLTN_RATE_60")
    private double PPLTN_RATE_60;       // 60대 실시간 인구 비율

    @JsonProperty("PPLTN_RATE_70")
    private double PPLTN_RATE_70;       // 70대 실시간 인구 비율

    @JsonProperty("RESNT_PPLTN_RATE")
    private double RESNT_PPLTN_RATE;    // 상주 인구 비율

    @JsonProperty("NON_RESNT_PPLTN_RATE")
    private double NON_RESNT_PPLTN_RATE;// 비상주 인구 비율

    @JsonProperty("REPLACE_YN")
    private String REPLACE_YN;          // 대체 데이터 여부

    @JsonProperty("PPLTN_TIME")
    private String PPLTN_TIME;          // 실시간 인구 데이터 업데이트 시간

    @JsonProperty("FCST_YN")
    private String FCST_YN;             // 예측값 제공 여부

    @JsonProperty("FCST_PPLTN")
    private String FCST_PPLTN;          // 인구 예측 오브젝트

    @JsonProperty("FCST_TIME")
    private String FCST_TIME;           // 인구 예측시점

    @JsonProperty("FCST_CONGEST_LVL")
    private String FCST_CONGEST_LVL;    // 장소 예측 혼잡도 지표

    @JsonProperty("FCST_PPLTN_MIN")
    private String FCST_PPLTN_MIN;      // 예측 실시간 인구 지표 최소값

    @JsonProperty("FCST_PPLTN_MAX")
    private String FCST_PPLTN_MAX;      // 예측 실시간 인구 지표 최대값
}

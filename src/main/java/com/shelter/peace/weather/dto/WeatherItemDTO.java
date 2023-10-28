package com.shelter.peace.weather.dto;

import lombok.Data;

@Data
public class WeatherItemDTO {
    // 기본 날짜 (yyyyMMdd)
    private String baseDate;
    // 기본 시간 (HHmm)
    private String baseTime;
    // 예보 카테고리
    private String category;
    // 예보 날짜 (yyyyMMdd)
    private String fcstDate;
    // 예보 시간 (HHmm)
    private String fcstTime;
    // 예보지점 X 좌표
    private int nx;
    // 예보지점 Y 좌표
    private int ny;
}

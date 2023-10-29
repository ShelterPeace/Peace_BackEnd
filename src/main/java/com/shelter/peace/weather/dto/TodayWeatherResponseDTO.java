package com.shelter.peace.weather.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TodayWeatherResponseDTO {
    // 온도
    private String temp;
    // 설명
    private String description;
    // 체감 온도
    private String feelTemp;
    // 습도
    private String humidity;
    // 풍속
    private String windSpeed;
    // 구름
    private String cloud;
    // 비 정보
    private boolean rainInfo;
    // 강수량
    private String rain1h;
    // 일출
    private String sunrise;
    // 일몰
    private String sunset;
    // 나라
    private String country;
}

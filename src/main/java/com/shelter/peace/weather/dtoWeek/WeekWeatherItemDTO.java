package com.shelter.peace.weather.dtoWeek;

import lombok.Getter;

import java.util.List;

@Getter
public class WeekWeatherItemDTO {
    private long dt;
    private WeekMainInfoDTO main;
    private List<WeekWeatherInfoDTO> weather;
    private WeekCloudsInfoDTO clouds;
    private WeekWindInfoDTO wind;
    private int visibility;
    private double pop;
    private WeekRainInfoDTO rain;
    private WeekSysInfoDTO sys;
    private String dt_txt;
}

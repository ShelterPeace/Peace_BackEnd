package com.shelter.peace.weather.dtoWeek;

import lombok.Getter;

import java.util.List;

@Getter
public class WeekWeatherResponseDTO {
    private String cod;
    private int message;
    private int cnt;
    private List<WeekWeatherItemDTO> list;
    private WeekCityDTO city;
}

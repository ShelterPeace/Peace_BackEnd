package com.shelter.peace.weather.dtoWeek;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeekWeatherInfoDTO {
    private int id;
    private String main;
    private String description;
    private String icon;
}

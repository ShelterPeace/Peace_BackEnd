package com.shelter.peace.weather.dtoToday;

import lombok.Getter;

@Getter
public class OpenWeatherDTO {
    private int id;
    private String main;
    private String description;
    private String icon;
}

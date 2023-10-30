package com.shelter.peace.weather.dtoWeek;

import lombok.Getter;

@Getter
public class WeekCityDTO {
    private int id;
    private String name;
    private WeekCoordDTO coord;
    private String country;
    private int population;
    private int timezone;
    private long sunrise;
    private long sunset;
}

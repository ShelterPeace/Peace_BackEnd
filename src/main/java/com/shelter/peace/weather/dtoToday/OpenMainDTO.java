package com.shelter.peace.weather.dtoToday;

import lombok.Getter;

@Getter
public class OpenMainDTO {
    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;
}

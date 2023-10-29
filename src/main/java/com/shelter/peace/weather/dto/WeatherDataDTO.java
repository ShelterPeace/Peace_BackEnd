package com.shelter.peace.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherDataDTO {
    private String date;
    private double temperature;
    private double minTemperature;
    private double maxTemperature;

}

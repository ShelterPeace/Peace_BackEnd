package com.shelter.peace.weather.dto;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeekWeatherResponseDTO {
    private String date;
    private double minTemperature;
    private double maxTemperature;
    private String rainInfo;
    private String message;
}

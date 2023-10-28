package com.shelter.peace.weather.dto;

import lombok.Data;

@Data
public class WeatherResponseDTO {
    WeatherHeaderDTO header;
    WeatherBodyDTO body;
}

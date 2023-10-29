package com.shelter.peace.weather.dtoToday;

import lombok.Getter;

import java.util.List;

@Getter
public class OpenWeatherResponseDTO {
    private OpenCoordDTO coord;
    private List<OpenWeatherDTO> weather;
    private String base;
    private OpenMainDTO main;
    private int visibility;
    private OpenWindDTO wind;
    private OpenCloudsDTO clouds;
    private long dt;
    private OpenSysDTO sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;
    private OpenRainDTO rain;
}

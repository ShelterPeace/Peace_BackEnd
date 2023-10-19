package com.shelter.peace.weather.dto;

import lombok.Data;

@Data
public class WeatherBodyDTO {
    private String dataType;
    private WeatherItemsDTO items;
    private int pageNo;
    private int numOfRows;
    private int totalCount;
}

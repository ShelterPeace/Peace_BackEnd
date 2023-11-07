package com.shelter.peace.weather.dtoWeek;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class WeekRainInfoDTO {
    @JsonProperty("3h")
    private double rain3h;
}

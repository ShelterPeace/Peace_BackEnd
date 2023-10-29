package com.shelter.peace.weather.dtoNow;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class OpenRainDTO {
    @JsonProperty("1h")
    private double rain1h;
}

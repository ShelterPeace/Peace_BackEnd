package com.shelter.peace.weather.dtoToday;

import lombok.Getter;

@Getter
public class OpenSysDTO {
    private int type;
    private int id;
    private String country;
    private long sunrise;
    private long sunset;
}

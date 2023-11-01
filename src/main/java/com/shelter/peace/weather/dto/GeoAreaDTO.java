package com.shelter.peace.weather.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class GeoAreaDTO {
    private String name;
    private Map<String, String> local_names;
    private double lat;
    private double lon;
    private String country;
}

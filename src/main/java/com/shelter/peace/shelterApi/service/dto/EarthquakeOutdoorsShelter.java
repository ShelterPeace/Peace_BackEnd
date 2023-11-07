package com.shelter.peace.shelterApi.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class EarthquakeOutdoorsShelter {
    private List<Map<String, Object>> head;
    private List<Row> row;
}
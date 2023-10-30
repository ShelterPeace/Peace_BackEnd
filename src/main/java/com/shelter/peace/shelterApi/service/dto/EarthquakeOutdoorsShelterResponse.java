package com.shelter.peace.shelterApi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class EarthquakeOutdoorsShelterResponse {
    @JsonProperty("EarthquakeOutdoorsShelter")
    private List<EarthquakeOutdoorsShelter> earthquakeOutdoorsShelters;
}
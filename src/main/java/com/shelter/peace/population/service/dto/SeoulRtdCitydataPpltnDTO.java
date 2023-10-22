package com.shelter.peace.population.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SeoulRtdCitydataPpltnDTO {
    @JsonProperty("SeoulRtd.citydata_ppltn")
    private List<PopulationDTO> populationDTOList;
}

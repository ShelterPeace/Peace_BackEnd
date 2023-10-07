package com.shelter.peace.shelterApi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shelter.peace.shelterApi.service.dto.EarthquakeShelterDTO;

import java.util.List;

public interface EarthquakeShelterService {
    List<EarthquakeShelterDTO> extractEarthquakeShelterData() throws JsonProcessingException;
}

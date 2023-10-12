package com.shelter.peace.shelterApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shelter.peace.shelterApi.service.CivilShelterService;
import com.shelter.peace.shelterApi.service.EarthquakeShelterService;
import com.shelter.peace.shelterApi.service.dto.EarthquakeShelterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shelter")
@RequiredArgsConstructor
public class ShelterApiController {

    private final CivilShelterService civilShelterService;
    private final EarthquakeShelterService earthquakeShelterService;

    //전국 민방위대피시설 데이터 저장
    @PostMapping("/upload")
    public void uploadCsvFile(@RequestParam("file") MultipartFile file) {
        civilShelterService.processCsvFile(file);
    }


    //전국 지진옥외대피시설 데이터 저장
    @PostMapping("/upload/EarthquakeShelterData")
    public ResponseEntity<String> SaveEarthquakeShelterData() {
        try {
            List<EarthquakeShelterDTO> extractedDataList = earthquakeShelterService.extractEarthquakeShelterData();
            return new ResponseEntity<>("정보가 저장되었습니다. " + extractedDataList.size() + " records.", HttpStatus.OK);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return new ResponseEntity<>("정보 저장에 실패하였습니다..", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

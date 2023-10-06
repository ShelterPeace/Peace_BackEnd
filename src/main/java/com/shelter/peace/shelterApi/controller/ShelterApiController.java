package com.shelter.peace.shelterApi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.shelter.peace.shelterApi.service.CivilShelterService;
import com.shelter.peace.shelterApi.service.EarthquakeShelterService;
import com.shelter.peace.shelterApi.service.dto.EarthquakeShelterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shelter")
public class ShelterApiController {
    @Autowired
    private CivilShelterService civilShelterService;
    private EarthquakeShelterService earthquakeShelterService;

    public ShelterApiController(EarthquakeShelterService earthquakeShelterService) {
        this.earthquakeShelterService = earthquakeShelterService;
    }

    //전국 민방위대피시설 데이터 저장
    @PostMapping("/upload")
    public void uploadCsvFile(@RequestParam("file") MultipartFile file) {
        civilShelterService.processCsvFile(file);
    }

    //지진옥외대피소 데이터 저장
    @GetMapping("/extractEarthquakeShelterData")
    public ResponseEntity<String> extractEarthquakeShelterData() {
        try {
            // EarthquakeShelterService를 이용하여 데이터 추출 및 저장
            List<EarthquakeShelterDTO> extractedDataList = earthquakeShelterService.extractEarthquakeShelterData();

            if (extractedDataList != null && !extractedDataList.isEmpty()) {
                return ResponseEntity.ok("지진옥외대피소 데이터를 추출하고 저장했습니다.");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 추출 또는 저장 중에 문제가 발생했습니다.");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("데이터 추출 또는 저장 중에 문제가 발생했습니다.");
        }
    }
}

package com.shelter.peace.shelterApi.controller;

import com.shelter.peace.shelterApi.service.CivilShelterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/shelter")
@RequiredArgsConstructor
public class ShelterApiController {

    private final CivilShelterService civilShelterService;

    //전국 민방위대피시설 데이터 저장
    @PostMapping("/upload")
    public void uploadCsvFile(@RequestParam("file") MultipartFile file) {
        civilShelterService.processCsvFile(file);
    }


}

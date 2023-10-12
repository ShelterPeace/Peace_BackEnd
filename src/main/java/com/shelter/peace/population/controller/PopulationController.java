package com.shelter.peace.population.controller;


import com.shelter.peace.dto.ResponseDTO;
import com.shelter.peace.population.service.dto.PopulationDTO;
import com.shelter.peace.population.service.PopulationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/population")
@RequiredArgsConstructor
public class PopulationController {
    private final PopulationService populationService;

    @GetMapping("/hotspot")
    public ResponseEntity abc(@RequestParam(name = "area") String area) {
        ResponseDTO<PopulationDTO> responseDTO = new ResponseDTO<>();
        System.out.println("area: " + area  );

        PopulationDTO populationDTO = populationService.population(area);

        responseDTO.setItem(populationDTO);
        responseDTO.setStatusCode(HttpStatus.OK.value());

        return ResponseEntity.ok().body(responseDTO);
    }

    //전국 민방위대피시설 데이터 저장
    @PostMapping("/upload")
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        populationService.parseExcel(file);
    }
}

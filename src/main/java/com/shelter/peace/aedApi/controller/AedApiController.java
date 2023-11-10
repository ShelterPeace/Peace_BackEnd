package com.shelter.peace.aedApi.controller;

import com.shelter.peace.aedApi.entity.AedEntity;
import com.shelter.peace.aedApi.repository.AEDRepository;
import com.shelter.peace.aedApi.service.AedApiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aed")
public class AedApiController {
    private final AedApiService aedApiService;
    private final AEDRepository aedRepository;
    public AedApiController(AedApiService aedApiService, AEDRepository aedRepository) {
        this.aedApiService = aedApiService;
        this.aedRepository = aedRepository;
    }

    @GetMapping("/fetch")
    public ResponseEntity<String> fetchAEDData() {
        try {
            aedApiService.fetchAEDData();
            return ResponseEntity.ok("조회 및 저장이 완료되었습니다.");
        } catch (Exception e) {
            // 로그 출력
            System.out.println("실패 이유: " + e.getMessage());
            return ResponseEntity.badRequest().body("실패하였습니다.");
        }
    }

    @GetMapping("/chooseData")
    public Page<AedEntity> getAedData(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return aedRepository.findAll(pageable);
    }
}

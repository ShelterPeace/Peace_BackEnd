package com.shelter.peace.aedApi.controller;

import com.shelter.peace.aedApi.service.AedApiService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aed")
public class AedApiController {
    private final AedApiService aedApiService;

    public AedApiController(AedApiService aedApiService) {
        this.aedApiService = aedApiService;
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
}

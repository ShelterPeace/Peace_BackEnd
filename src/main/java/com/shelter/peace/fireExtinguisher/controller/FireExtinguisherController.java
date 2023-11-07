package com.shelter.peace.fireExtinguisher.controller;

import com.shelter.peace.fireExtinguisher.entity.FireExtinguisher;
import com.shelter.peace.fireExtinguisher.entity.GyeonggiFireExt;
import com.shelter.peace.fireExtinguisher.repository.FireExtinguisherRepository;
import com.shelter.peace.fireExtinguisher.service.FireExtinguisherService;
import com.shelter.peace.fireExtinguisher.service.GyeonggiFireExtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fireExt")
public class FireExtinguisherController {
    private static final Logger logger = LoggerFactory.getLogger(FireExtinguisherController.class);

    private final FireExtinguisherService fireExtinguisherService;
    private final FireExtinguisherRepository fireExtinguisherRepository;

    private final GyeonggiFireExtService gyeonggiFireExtService;
    public FireExtinguisherController(FireExtinguisherService fireExtinguisherService, FireExtinguisherRepository fireExtinguisherRepository, GyeonggiFireExtService gyeonggiFireExtService) {
        this.fireExtinguisherService = fireExtinguisherService;
        this.fireExtinguisherRepository = fireExtinguisherRepository;
        this.gyeonggiFireExtService = gyeonggiFireExtService;
    }

    @GetMapping("/seoul/fetch")
    public ResponseEntity<String> fetchFireExtinguisherData() {
        try {
            fireExtinguisherService.fetchFireExtinguisherData();
            return ResponseEntity.ok("조회 및 저장이 완료되었습니다");
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.status(500).body("실패하였습니다");        }
    }

    @GetMapping("/seoul/list")
    public ResponseEntity<Page<FireExtinguisher>> getFireExtinguisherList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(fireExtinguisherService.getFireExtinguisherList(pageable));
    }

    @GetMapping("/gyeonggi/fetch")
    public ResponseEntity<String> fetchGyeonggiFireExtData() {
        try {
            gyeonggiFireExtService.fetchGyeonggiFireExtData();
            return ResponseEntity.ok("조회 및 저장이 완료되었습니다");
        } catch (Exception e) {
            logger.error("Error: ", e);
            return ResponseEntity.status(500).body("실패하였습니다");
        }
    }

    @GetMapping("/gyeonggi/list")
    public ResponseEntity<Page<GyeonggiFireExt>> getGyeonggiFireExtList(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(gyeonggiFireExtService.getGyeonggiFireExtList(pageable));
    }

}

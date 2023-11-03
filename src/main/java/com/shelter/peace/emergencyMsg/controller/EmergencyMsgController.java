package com.shelter.peace.emergencyMsg.controller;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.repository.MsgRepository;
import com.shelter.peace.emergencyMsg.service.MsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msg")
public class EmergencyMsgController {
    private final MsgService msgService;
    private final MsgRepository msgRepository;

    @Autowired
    public EmergencyMsgController(MsgService msgService, MsgRepository msgRepository) {
        this.msgService = msgService;
        this.msgRepository = msgRepository;
    }

    // 데이터 수동 저장(최초 1회 실행 후 자동으로 업데이트 됩니다.)
    @GetMapping("/disaster")
    public ResponseEntity<String> extractDisasterMsgData() {
        try {
            msgService.extractDisasterMsgData();
            return ResponseEntity.ok("저장 성공");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("저장 실패: " + e.getMessage());
        }
    }

    // 전체 내역 보기
    @GetMapping("/all")
    public List<DisasterMsg> getAllDisasterMessages() {
        return msgRepository.findAll(); // 모든 데이터를 조회
    }
}


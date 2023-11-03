package com.shelter.peace.emergencyMsg.controller;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.repository.MsgRepository;
import com.shelter.peace.emergencyMsg.service.MsgService;
import com.shelter.peace.emergencyMsg.service.impl.KeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/msg")
public class EmergencyMsgController {
    private final MsgService msgService;
    private final MsgRepository msgRepository;
    private final KeywordService keywordService;

    @Autowired
    public EmergencyMsgController(MsgService msgService, MsgRepository msgRepository, KeywordService keywordService) {
        this.msgService = msgService;
        this.msgRepository = msgRepository;
        this.keywordService = keywordService;
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

    //각 키워드의 내용 보기
    @GetMapping("/processKeywords")
    public String processKeywords(@RequestParam(value = "keywords", required = false) String[] keywords) {
        if (keywords == null || keywords.length == 0) {
            return "적어도 하나의 키워드를 선택하세요.";
        }

        StringBuilder result = new StringBuilder();
        for (String keyword : keywords) {
            String sentence = keywordService.processMessageForKeywords(keyword);
            if (sentence != null) {
                result.append(sentence).append("\n");
            }
        }

        if (result.length() == 0) {
            return "선택한 키워드에 대한 내용을 찾을 수 없습니다.";
        }

        return result.toString();
    }

}


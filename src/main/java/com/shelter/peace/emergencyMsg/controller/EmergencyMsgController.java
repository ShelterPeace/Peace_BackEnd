package com.shelter.peace.emergencyMsg.controller;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.entity.UserNotification;
import com.shelter.peace.emergencyMsg.repository.MsgRepository;
import com.shelter.peace.emergencyMsg.service.MsgService;
import com.shelter.peace.emergencyMsg.service.dto.FilteredDisasterMsgDto;
import com.shelter.peace.emergencyMsg.service.impl.*;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/msg")
public class EmergencyMsgController {
    private final MsgService msgService;
    private final MsgRepository msgRepository;
    private final KeywordService keywordService;
    private final UserKeywordService userKeywordService;
    private final UserNotificationService userNotificationService;
    private final LocationMsgService locationMsgService;

    private final MsgFilterService msgFilterService;
    @Autowired
    public EmergencyMsgController(MsgService msgService, MsgRepository msgRepository, KeywordService keywordService, UserKeywordService userKeywordService, UserNotificationService userNotificationService, LocationMsgService locationMsgService, MsgFilterService msgFilterService) {
        this.msgService = msgService;
        this.msgRepository = msgRepository;
        this.keywordService = keywordService;
        this.userKeywordService = userKeywordService;
        this.userNotificationService = userNotificationService;
        this.locationMsgService = locationMsgService;
        this.msgFilterService = msgFilterService;
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

    // 각 키워드의 내용 보기
    @GetMapping("/processKeywords")
    public String processKeywords(@RequestParam(value = "keywords", required = false) String[] keywords) {
        if (keywords == null || keywords.length == 0) {
            return "적어도 하나의 키워드를 선택하세요.";
        }

        StringBuilder result = new StringBuilder();
        for (String keyword : keywords) {
            String sentence = keywordService.processMessageForKeywords(keyword);
            if (!sentence.isEmpty()) {
                result.append("키워드: ").append(keyword).append("\n");
                result.append("키워드 관련 문장: ").append(sentence).append("\n\n");
            }
        }

        if (result.length() == 0) {
            return "선택한 키워드에 대한 내용을 찾을 수 없습니다.";
        }

        return result.toString();
    }

    // 모든 키워드 및 관련 문장 가져오기
    @GetMapping("/processKeywords/all")
    public ResponseEntity<Map<String, List<String>>> getAllKeywords() {
        // KeywordService에서 키워드와 관련 문장 정보를 가져옵니다.
        Map<String, List<String>> keywordToSentences = keywordService.getKeywordToSentences();
        return ResponseEntity.ok(keywordToSentences);
    }

    //사용자 키워드 추가하기
    @PostMapping("/keyword")
    public ResponseEntity<String> addUserKeyword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam List<String> keyword) {
        try {
            if (userDetails == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
            }
            String username = userDetails.getUsername();
            List<String> results = new ArrayList<>();
            for (String kw : keyword) {
                String result = userKeywordService.saveUserKeyword(username, kw);
                if (!result.equals("키워드 저장이 완료되었습니다")) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
                }
                results.add(result);
            }
            return ResponseEntity.ok("키워드가 성공적으로 추가되었습니다.");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }
    // 사용자 키워드 삭제하기
    @DeleteMapping("/keyword")
    public ResponseEntity<String> deleteUserKeyword(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam List<String> keyword) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        String username = userDetails.getUsername();
        for (String kw : keyword) {
            userKeywordService.deleteUserKeyword(username, kw);
        }
        return ResponseEntity.ok("키워드가 성공적으로 삭제되었습니다.");
    }

    //사용자별로 알림내용 확인하기
    @GetMapping("/notifications")
    public ResponseEntity<List<UserNotification>> getUserNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        String userId = userDetails.getUsername();
        List<UserNotification> notifications = userNotificationService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    // 사용자가 원하는 지역을 설정
    @PostMapping("/location")
    public ResponseEntity<String> setUserLocation(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam List<String> locationNames) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = userDetails.getId();
        try {
            for (String locationName : locationNames) {
                locationMsgService.setUserLocation(userId, locationName);
            }
            return ResponseEntity.ok("지역 설정이 성공적으로 완료되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 사용자가 설정한 지역을 삭제하는 API
    @DeleteMapping("/location")
    public ResponseEntity<String> deleteUserLocation(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestParam List<String> locationNames) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = userDetails.getId();
        try {
            for (String locationName : locationNames) {
                locationMsgService.deleteUserLocation(userId, locationName);
            }
            return ResponseEntity.ok("지역 설정이 성공적으로 삭제되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 로그인한 사용자의 지역 정보에 맞는 재난 문자 데이터를 가져오는 API
    @GetMapping("/location/disasterMsgs")
    public ResponseEntity<?> getDisasterMsgsForUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = userDetails.getId();
        List<DisasterMsg> disasterMsgs = locationMsgService.getDisasterMsgsForUser(userId);
        if (disasterMsgs == null || disasterMsgs.isEmpty()) {
            return ResponseEntity.ok().body("해당하는 재난문자가 없습니다.");
        }
        return ResponseEntity.ok(disasterMsgs);
    }

    // 로그인한 사용자의 지역 및 키워드 설정에 따라 필터링된 재난문자를 가져오는 API
    @GetMapping("/filtered/disasterMsgs")
    public ResponseEntity<?> getFilteredDisasterMsgsForUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        Long userId = userDetails.getId();
        List<FilteredDisasterMsgDto> filteredDisasterMsgs = msgFilterService.getFilteredDisasterMsgsForUser(userId);

        if (filteredDisasterMsgs.isEmpty()) {
            return ResponseEntity.ok().body("해당하는 재난문자가 없습니다.");
        }

        for (FilteredDisasterMsgDto filteredDisasterMsg : filteredDisasterMsgs) {
            List<String> matchingKeywords = filteredDisasterMsg.getMatchingKeywords();
            List<String> matchingKeywordSentences = new ArrayList<>();

            for (String keyword : matchingKeywords) {
                String keywordSentence = keywordService.processMessageForKeywords(keyword);
                if (!keywordSentence.isEmpty()) {
                    matchingKeywordSentences.add(keywordSentence);
                }
            }
            filteredDisasterMsg.setMatchingKeywordSentences(matchingKeywordSentences);
        }

        return ResponseEntity.ok(filteredDisasterMsgs);
    }



}



package com.shelter.peace.emergencyMsg.service.impl;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class KeywordService {
    private Map<String, String> keywordToSentence;

    public KeywordService() {
        keywordToSentence = new HashMap<>();
        keywordToSentence.put("산사태", "산사태에 관한 안내사항 입니다.");
        keywordToSentence.put("폭우", "폭우에 관한 안내사항 입니다.");
        keywordToSentence.put("태풍", "태풍에 관한 안내사항 입니다.");
        keywordToSentence.put("실종", "실종에 관한 안내사항 입니다.");
        keywordToSentence.put("호우", "호우에 관한 안내사항 입니다.");
        keywordToSentence.put("지진", "지진에 관한 안내사항 입니다.");
        keywordToSentence.put("해일", "해일에 관한 안내사항 입니다.");
        keywordToSentence.put("교통", "교통에 관한 안내사항 입니다.");
        keywordToSentence.put("폭염", "폭염에 관한 안내사항 입니다.");
        keywordToSentence.put("강풍", "강풍에 관한 안내사항 입니다.");
        // 다른 키워드와 문장 추가
    }

    public String processMessageForKeywords(String message) {
        for (Map.Entry<String, String> entry : keywordToSentence.entrySet()) {
            String keyword = entry.getKey();
            String sentence = entry.getValue();

            if (message.contains(keyword)) {
                return sentence;
            }
        }
        // 키워드가 없는 경우 처리
        return "설정된 키워드에 해당하지 않습니다.";
    }

}

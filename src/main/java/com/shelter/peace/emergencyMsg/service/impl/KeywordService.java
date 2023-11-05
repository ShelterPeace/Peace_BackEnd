package com.shelter.peace.emergencyMsg.service.impl;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class KeywordService {
    private Map<String, List<String>> keywordToSentences;

    public KeywordService() {
        keywordToSentences = new HashMap<>();

        //대표키워드 : 실종
        List<String> aKeywords = new ArrayList<>();
        aKeywords.add("목격");
        aKeywords.add("배회");
        // "실종"에 대한 문장
        List<String> aSentences = new ArrayList<>();
        aSentences.add("실종 관련 안내사항");
        keywordToSentences.put("실종", aKeywords);
        keywordToSentences.put("실종", aSentences);

        //대표키워드 : 교통
        List<String> bKeywords = new ArrayList<>();
        bKeywords.add("운전");
        bKeywords.add("차량");
        // "교통"에 대한 문장
        List<String> bSentences = new ArrayList<>();
        bSentences.add("교통 관련 안내사항");
        keywordToSentences.put("교통", bKeywords);
        keywordToSentences.put("교통", bSentences);

        //대표키워드 : 화재
        List<String> cKeywords = new ArrayList<>();
        cKeywords.add("산불");
        // "화재"에 대한 문장
        List<String> cSentences = new ArrayList<>();
        cSentences.add("화재 관련 안내사항");
        keywordToSentences.put("화재", cKeywords);
        keywordToSentences.put("화재", cSentences);

        //대표키워드 : 지진
        List<String> dKeywords = new ArrayList<>();
        dKeywords.add("여진발생");
        // "지진"에 대한 문장
        List<String> dSentences = new ArrayList<>();
        dSentences.add("지진 관련 안내사항");
        keywordToSentences.put("지진", dKeywords);
        keywordToSentences.put("지진", dSentences);

        //대표키워드 : 태풍
        List<String> eKeywords = new ArrayList<>();
        eKeywords.add("강풍");
        eKeywords.add("폭풍");
        // "태풍"에 대한 문장
        List<String> eSentences = new ArrayList<>();
        eSentences.add("태풍 및 강풍 관련 안내사항");
        keywordToSentences.put("태풍", eKeywords);
        keywordToSentences.put("태풍", eSentences);

        //대표키워드 : 호우
        List<String> fKeywords = new ArrayList<>();
        fKeywords.add("비");
        fKeywords.add("침수");
        fKeywords.add("홍수");
        // "호우"에 대한 문장
        List<String> fSentences = new ArrayList<>();
        fSentences.add("호우 및 비 관련 안내사항");
        keywordToSentences.put("호우", fKeywords);
        keywordToSentences.put("호우", fSentences);

        //대표키워드 : 해일
        List<String> gKeywords = new ArrayList<>();
        gKeywords.add("해안 저지대");
        // "해일"에 대한 문장
        List<String> gSentences = new ArrayList<>();
        gSentences.add("해일 관련 안내사항");
        keywordToSentences.put("해일", gKeywords);
        keywordToSentences.put("해일", gSentences);

        //대표키워드 : 산사태
        List<String> hKeywords = new ArrayList<>();
        hKeywords.add("산사태위기경보");
        // "산사태"에 대한 문장
        List<String> hSentences = new ArrayList<>();
        hSentences.add("산사태 관련 안내사항");
        keywordToSentences.put("산사태", hKeywords);
        keywordToSentences.put("산사태", hSentences);

        //대표키워드 : 폭염
        List<String> iKeywords = new ArrayList<>();
        iKeywords.add("무더위 쉼터");
        iKeywords.add("수분섭취");
        // "폭염"에 대한 문장
        List<String> iSentences = new ArrayList<>();
        iSentences.add("폭염 관련 안내사항");
        keywordToSentences.put("폭염", iKeywords);
        keywordToSentences.put("폭염", iSentences);

    }

    public String processMessageForKeywords(String message) {
        for (Map.Entry<String, List<String>> entry : keywordToSentences.entrySet()) {
            String representativeKeyword = entry.getKey();
            List<String> relatedKeywords = entry.getValue();

            if (message.contains(representativeKeyword) || relatedKeywords.stream().anyMatch(message::contains)) {
                return getKeywordSentences(representativeKeyword);
            }
        }
        return ""; // 아무 내용도 반환하지 않음
    }

    private String getKeywordSentences(String keyword) {
        List<String> sentences = keywordToSentences.get(keyword);
        if (sentences != null) {
            return String.join("\n", sentences);
        }
        return "";
    }

    public Map<String, List<String>> getKeywordToSentences() {
        return keywordToSentences;
    }

    public boolean isValidKeyword(String keyword) {
        // keywordToSentences 맵에서 키워드가 유효한지 확인
        for (Map.Entry<String, List<String>> entry : keywordToSentences.entrySet()) {
            String representativeKeyword = entry.getKey();
            List<String> relatedKeywords = entry.getValue();

            if (representativeKeyword.equals(keyword) || relatedKeywords.contains(keyword)) {
                return true;
            }
        }

        return false; // 대표 키워드 또는 관련 키워드 목록에 없는 경우
    }
}

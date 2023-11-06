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
        aSentences.add("실종(목격,배회) 관련 안내사항이 있어요! \n" +"\n자세한 행동 요령 바로가기 URL → \n");
        keywordToSentences.put("실종", aKeywords);
        keywordToSentences.put("실종", aSentences);

        //대표키워드 : 교통
        List<String> bKeywords = new ArrayList<>();
        bKeywords.add("운전");
        bKeywords.add("차량");
        // "교통"에 대한 문장
        List<String> bSentences = new ArrayList<>();
        bSentences.add("교통 관련 안내사항이 있어요! \n" +"\n자세한 행동 요령 바로가기 URL → \n");
        keywordToSentences.put("교통", bKeywords);
        keywordToSentences.put("교통", bSentences);

        //대표키워드 : 화재
        List<String> cKeywords = new ArrayList<>();
        cKeywords.add("산불");
        // "화재"에 대한 문장
        List<String> cSentences = new ArrayList<>();
        cSentences.add("화재 관련 안내사항이 있어요! \n" +"\n자세한 행동 요령 바로가기 URL → \n");
        keywordToSentences.put("화재", cKeywords);
        keywordToSentences.put("화재", cSentences);

        //대표키워드 : 지진
        List<String> dKeywords = new ArrayList<>();
        dKeywords.add("여진발생");
        // "지진"에 대한 문장
        List<String> dSentences = new ArrayList<>();
        dSentences.add("● 지진 발생 시 이렇게 행동하세요! ●\n" +
                "\n" +
                "1. 지진으로 흔들릴 때는 탁자 아래로 들어가 몸을 보호하고 탁자 다리를 꼭 잡습니다.\n" +
                "2. 흔들림이 멈췄을 때는 전기와 가스를 차단하고, 문을 열어 출구를 확보합니다.\n" +
                "3. 건물 밖으로 나갈 때는 계단을 이용하여 신속하게 이동합니다. 엘리베이터는 사용하지 않습니다.\n" +
                "4. 건물 밖으로 나왔을 때는 가방이나 손으로 머리를 보호하며, 건물과 거리를 두고 주위를 살피며 대피합니다.\n" +
                "5. 대피 장소를 찾을 때는 떨어지는 물건에 유의하며 신속하게 운동장이나 공원 등 넓은 공간으로 대피합니다.\n" +
                "6. 대피 장소에 도착한 후에는 라디오나 공공기관의 안내 방송 등 올바른 정보에 따라 행동합니다.\n" +
                "\n" +
                "어린이와 함께 있을 때에는 유모차 보다는 아기띠를 사용합니다. 지진 상황에서는 유리 파편, 건물 잔해 등으로 도로가 위험하기 때문에 걸을 수 있는 아이라도 안고 대피하고, 업을 때에는 머리를 보호하고, 안거나 업더라도 반드시 신발을 신깁니다.\n" +
                "\n" +
                "몸이 불편하신 분은 혼자서 행동하지 말고 이웃과 함께 대피하며, 휠체어나 보행기를 사용하는 경우에는 바퀴를 잠그고 몸을 앞으로 숙이고, 책 ‧방석‧베개 등으로 머리와 목을 보호하며, 야외 넓은 장소 또는 대피장소에 가지 않고 자택에 머무르는 경우에는 이웃이나 관공서 직원 등에게 그 사실을 알리고 고립되지 않도록 합니다.\n" +
                "\n" +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                "☎ 재난 신고전화 (전국어디서나 1588-3650)\n" +
                " ☎ 재난 신고 119");
        keywordToSentences.put("지진", dKeywords);
        keywordToSentences.put("지진", dSentences);

        //대표키워드 : 태풍
        List<String> eKeywords = new ArrayList<>();
        eKeywords.add("강풍");
        eKeywords.add("폭풍");
        // "태풍"에 대한 문장
        List<String> eSentences = new ArrayList<>();
        eSentences.add(" ☁  태풍 및 강풍 주의, 이럴 땐 이렇게 행동하세요!  ☁ \n" +
                "\n" +
                "실내에서의 안전을 위해 건물 출입문과 창문을 닫고, 유리문이나 창문에서 떨어진 곳에 머무르며, \n" +
                "강풍에 안전한 공간으로 이동해야 합니다. 또한, 가스와 전기시설은 미리 차단하고 만지지 않으며,\n" +
                "정전 시에는 양초 대신 휴대용 랜턴이나 휴대폰을 사용하는 것이 중요합니다.\n" +
                "\n" +
                "태풍이나 호우 시에는 지붕이나 간판 등의 결박 작업을 하고, 창문을 테이프로 고정해야 합니다. \n" +
                "또한, 차량은 안전한 곳으로 이동시키고, 하수구나 배수구의 점검, 침수 예방을 위한 준비 작업이 필요합니다. \n" +
                "농업 시설물이나 선박은 버팀목이나 비닐 끈 등으로 결박하고, 하천 주변 접근, 침수된 도로 통행, 유리창 및 건물 간판 근처 접근, 세월교나 소규모 교량 횡단, 물꼬 보러 나가는 행동을 삼가야 합니다. 태풍으로 인한 낙뢰 발생 시에는 안전한 장소로 대피하는 것이 중요합니다.\n" +
                "\n" +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                " ☎ 재난 신고전화 (전국어디서나 1588-3650)\n" +
                " ☎ 재난 신고 119");
        keywordToSentences.put("태풍", eKeywords);
        keywordToSentences.put("태풍", eSentences);

        //대표키워드 : 호우
        List<String> fKeywords = new ArrayList<>();
        fKeywords.add("비");
        fKeywords.add("침수");
        fKeywords.add("홍수");
        fKeywords.add("폭우");
        // "호우"에 대한 문장
        List<String> fSentences = new ArrayList<>();
        fSentences.add("fSentences.add(\"☂ 호우 및 침수, 비가 많이 내릴 땐 이렇게 행동하세요!  ☂\\n\\n[지하 공간 행동요령] : 지하공간에 물이 차오르면 즉시 대피하되, " +
                "외부수심이 무릎 이상일 경우 전기를 차단하고 문을 여러명이서 열어 대피하세요. 침수된 주택에 들어가기 전에는 환기를 시키고, " +
                "가스와 전기차단기 상태를 확인한 후 전문가의 안전점검을 받아야 합니다.\\n\\n[차량 이용자 행동요령] : 차량이 침수되기 시작하면 가능한 빨리 안전한 곳으로 이동시키고, " +
                "만약 차량 문이 열리지 않는다면 유리창을 깨서 대피하세요. 지하차도나 급류가 있는 교량은 절대 진입하지 않으며, 교량에서 차량이 고립되면 반대쪽 문을 열어 탈출하세요.\\n\\n침수 위험지역, " +
                "산과 계곡, 공사장 근처는 휩쓸릴 위험이 있으므로 접근을 피하고, 농촌에서는 논둑이나 물꼬 점검을 위해 외출하지 않는 것이 중요합니다\\n 또한, 홍수 예상 시 전기와 가스를 차단하고, " +
                "침수된 지역에서의 자동차 운전은 피해야 합니다. " +
                "홍수 발생 시 높은 곳으로 대피하고, 침수 위험이 있는 지역의 통행은 안되요!\\n\\n " +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                "☎ 재난 신고전화 (전국어디서나 1588-3650)\\n ☎ 재난 신고 119\");\n");
        keywordToSentences.put("호우", fKeywords);
        keywordToSentences.put("호우", fSentences);

        //대표키워드 : 해일
        List<String> gKeywords = new ArrayList<>();
        gKeywords.add("해안 저지대");
        // "해일"에 대한 문장
        List<String> gSentences = new ArrayList<>();
        gSentences.add(" ●  해일 발생 시 이렇게 행동하세요!  ●  \n" +
                "\n" +
                "공사 중인 현장에서는 작업 중지하고 파손 우려가 있는 기자재를 안전한 곳으로 이동시켜야 합니다. 해일경보나 대피명령이 있을 경우 즉시 대피해야 하며, 해일이 밀려들 때에도 대피해야 합니다. \n" +
                "\n" +
                "1층보다는 2층, 2층보다는 3층, 때에 따라서는 지붕이 안전하니 높은 곳으로 이동하고, 목조 주택은 철근콘크리트 건물로 이동하는 것이 안전합니다. 해안에서 멀리 떨어진 안전한 곳으로 이동해야 하며, 해안에서 멀리 떨어진 곳은 직접적인 파도의 영향이 없으므로 바닥에서 높이가 2~3m만 높아도 비교적 안전합니다. \n" +
                "\n" +
                "해안가에서 조업 중인 선박은 가능한 수심이 깊은 지역으로 이동시켜야 합니다. 지진해일이 발생하면 선박의 안전에 특히 주의해야 하며, 육지에 있다면 신속히 고지대로 대피해야 합니다. 지진해일의 특성을 이해하고, 해안에 영향을 미칠 때까지 안전한 대피장소에 머무는 것이 중요합니다.\n" +
                "\n" +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                " ☎ 재난 신고전화 (전국어디서나 1588-3650)\n" +
                " ☎ 재난 신고 119");
        keywordToSentences.put("해일", gKeywords);
        keywordToSentences.put("해일", gSentences);

        //대표키워드 : 산사태
        List<String> hKeywords = new ArrayList<>();
        hKeywords.add("산사태위기경보");
        // "산사태"에 대한 문장
        List<String> hSentences = new ArrayList<>();
        hSentences.add(" ●  산사태 발생 시 이렇게 행동하세요!  ●  \n" +
                "\n" +
                "산 주변의 야외활동은 하지 않으며, 산지 인근 논둑이나 물꼬의 점검을 위해도 나가지 않습니다.\n" +
                "\n" +
                "야외활동 중이었다면 산지에서 떨어진 안전한 장소로 이동하고, 지역 주민은 대피 명령이 발령되면 마을회관이나 학교 등 사전에 지정된 장소로 대피합니다. 건물에 들어갈 때는 손상 여부를 확인하고 들어가며, 토사 유입이 우려되는 지하주차장은 접근을 자제합니다. \n" +
                "\n" +
                "대피 이동 중에는 고압전선 인근으로의 접근을 자제하고, 위험한 경우에는 튼튼한 건물 등 안전한 장소로 이동합니다. 대피할 수 없어 건물 안에 머무르는 경우에는 가능한 건물에서 가장 높은 층이나 산과 멀리 떨어진 공간으로 대피하고, 몸을 보호합니다. \n" +
                "\n" +
                "고립된 상황에서는 소방서(119)에 구조를 요청하거나 구조를 요청하기 위해 소리를 지르거나 호루라기를 불거나 물건을 두드리는 등의 방법을 사용합니다. 문을 열 때는 옷장이나 사무실 보관함 등의 내용물이 쏟아져내려 부상을 입을 수 있으므로 주의해야 합니다.\n" +
                "\n" +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                " ☎ 재난 신고전화 (전국어디서나 1588-3650)\n" +
                " ☎ 재난 신고 119");
        keywordToSentences.put("산사태", hKeywords);
        keywordToSentences.put("산사태", hSentences);

        //대표키워드 : 폭염
        List<String> iKeywords = new ArrayList<>();
        iKeywords.add("무더위 쉼터");
        iKeywords.add("수분섭취");
        // "폭염"에 대한 문장
        List<String> iSentences = new ArrayList<>();
        iSentences.add(" ●  폭염 발생 시 이렇게 행동하세요!  ●  \n" +
                "\n" +
                "폭염은 열사병, 열경련 등의 온열질환을 유발할 수 있으며, 심하면 사망에 이르게 됩니다.\n" +
                "뿐만 아니라, 가축·수산물 폐사 등의 재산피해와 여름철 전력 급증 등으로 생활의 불편을 초래하기도 합니다.\n" +
                "\n" +
                "1. 외출 시에는 창이 긴 모자, 햇빛 가리개, 썬크림 등 차단제를 준비합니다.\n" +
                "2. 정전에 대비하여 손전등, 비상 식음료, 부채, 휴대용 라디오 등을 확인해둡니다.\n" +
                "3. 단수에 대비하여 생수를 준비하고, 욕조에 생활용수를 미리 받아둡니다.\n" +
                "4. 오래된 주택은 변압기를 사전에 점검하여 과부하에 대비합니다.\n" +
                "5. 물을 많이 마시고, 카페인이 들어간 음료나 주류는 마시지 않습니다.\n" +
                "6. 실내에서 냉방이 되지 않는 경우에는 햇볕을 가리고 맞바람이 불도록 환기합니다.\n" +
                "7. 현기증, 메스꺼움, 두통, 근육경련 등의 증세가 나타나면 시원한 곳으로 이동하여 휴식을 취하고 시원한 음료를 천천히 마십니다.\n" +
                "8. 설 현장 등 실외 작업장에서는 폭염안전수칙을 준수하고, 특히 취약시간인 오후 2~5시에는 무더위 휴식시간제를 적극 시행합니다\n" +
                "\n" +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                " ☎ 재난 신고전화 (전국어디서나 1588-3650)\n" +
                " ☎ 재난 신고 119");
        keywordToSentences.put("폭염", iKeywords);
        keywordToSentences.put("폭염", iSentences);

        //대표키워드 : 대설
        List<String> jKeywords = new ArrayList<>();
        jKeywords.add("한파");
        // "폭염"에 대한 문장
        List<String> jSentences = new ArrayList<>();
        jSentences.add("☃ 대설, 한파 주의, 눈이 많이 내릴 땐 이렇게 행동하세요! ☃" +
        "\n\n대설은 짧은 시간에 급격히 눈이 쌓이게 되므로 눈사태, 교통 혼잡, 쌓인 눈으로 인한 시설물 붕괴 등의 피해가 발생될 수 있습니다." +
                "\n\n한파로 인한 저체온증, 동상을 포함한 질병 위험에 대비하여 보온과 난방 관리에 주의하며, 차량 점검과 스노체인 준비를 하세요." +
                "\n또한, 정전이나 단수에 대비 하여 필요한 비상용품과 식음료를 준비하고, 가정 및 차량의 안전 점검을 하며, 차량 고립 시에는 119에 신고하고 구조를 기다리세요." +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                "\n\n ☎ 재난 신고전화 (전국어디서나 1588-3650)" +
                "\n ☎ 재난 신고 119");
        keywordToSentences.put("대설", jKeywords);
        keywordToSentences.put("대설", jSentences);

        //대표키워드 : 화산
        List<String> kKeywords = new ArrayList<>();
        kKeywords.add("화산폭발");
        kKeywords.add("화산재");
        kKeywords.add("용암");
        // "폭염"에 대한 문장
        List<String> kSentences = new ArrayList<>();
        kSentences.add(" ●  화산 폭발 시 이렇게 행동하세요!  ●  \n" +
                "\n" +
                "화산재 낙하 전:\n" +
                "* 문틈과 환기구를 수건으로 막고, 창문은 테이프로 막습니다.\n" +
                "* 배수로를 홈통으로부터 분리하여 화산재로 막히지 않도록 합니다.\n" +
                "화산재 낙하 중:\n" +
                "* 가능한 실내에 머무르고 TV나 라디오로 재난 방송을 청취합니다.\n" +
                "* 마스크와 옷으로 코와 입을 막고, 자동차나 건물로 신속하게 대피합니다.\n" +
                "화산재 낙하 후:\n" +
                "* 고글과 마스크를 착용하고 실내‧외 및 자동차를 신속하게 청소합니다.\n" +
                "* 밖에서 입은 옷은 갈아입고 몸을 깨끗이 씻고, 수거한 화산재는 튼튼한 비닐봉지에 넣어 버립니다.\n" +
                "\n" +
                "\n자세한 행동 요령 바로가기 URL → \n" +
                " ☎ 재난 신고전화 (전국어디서나 1588-3650)\n" +
                " ☎ 재난 신고 119");
        keywordToSentences.put("화산", kKeywords);
        keywordToSentences.put("화산", kSentences);
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

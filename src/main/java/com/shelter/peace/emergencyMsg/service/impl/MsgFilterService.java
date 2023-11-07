package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.entity.UserKeyword;
import com.shelter.peace.emergencyMsg.service.dto.FilteredDisasterMsgDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MsgFilterService {
    @Autowired
    private LocationMsgService locationMsgService;
    @Autowired
    private UserKeywordService userKeywordService;

    /*
    * 문자의 내용이 <msg>[영등포구]오늘 밤 강한 비바람 예상. 집주변 빗물받이 막히지 않게 낙엽제거, 저지대 물막이판 점검과 도림천 등 하천출입 삼가,
    * 공사장 안전관리 등 유의바랍니다</msg> 라고 온다고 가정할 때,
    * 내가 설정한 지역은 '서울' 로 하고 키워드는 '비' 라고 했으면 '비'는 저 문자에 있지만 '서울'은 없으니까 안뜬다.
    * */
    public List<FilteredDisasterMsgDto> getFilteredDisasterMsgsForUser(Long userId) {
        // 사용자가 설정한 지역에 해당하는 재난문자
        List<DisasterMsg> disasterMsgsByLocation = locationMsgService.getDisasterMsgsForUser(userId);
        // 사용자가 설정한 모든 키워드
        List<UserKeyword> userKeywords = userKeywordService.getAllUserKeywords();

        List<FilteredDisasterMsgDto> filteredDisasterMsgDtos = new ArrayList<>();
        for (DisasterMsg disasterMsg : disasterMsgsByLocation) {
            List<String> matchingLocations = new ArrayList<>();
            List<String> matchingKeywords = new ArrayList<>();
            for (UserKeyword userKeyword : userKeywords) {
                // 재난문자의 위치 정보(locationName)와 내용(message) 모두에서 키워드를 찾아 필터링
                if (disasterMsg.getLocationName().contains(userKeyword.getKeyword())) {
                    matchingLocations.add(disasterMsg.getLocationName());
                    matchingKeywords.add(userKeyword.getKeyword());
                } else if (disasterMsg.getMessage().contains(userKeyword.getKeyword())) {
                    matchingLocations.add(disasterMsg.getLocationName());
                    matchingKeywords.add(userKeyword.getKeyword());
                }
            }
            // 일치하는 위치나 키워드가 있다면, 필터링된 재난문자와 해당하는 위치 및 키워드를 함께 DTO에 담아 리스트에 추가
            if (!matchingLocations.isEmpty() && !matchingKeywords.isEmpty()) {
                FilteredDisasterMsgDto dto = new FilteredDisasterMsgDto();
                dto.setDisasterMsg(disasterMsg);
                dto.setMatchingLocations(matchingLocations);
                dto.setMatchingKeywords(matchingKeywords);
                filteredDisasterMsgDtos.add(dto);
            }
        }
        // 필터링된 재난문자와 해당하는 위치 및 키워드를 담은 DTO 리스트를 반환
        return filteredDisasterMsgDtos;
    }


}

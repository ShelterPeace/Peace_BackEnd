package com.shelter.peace.emergencyMsg.service.dto;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FilteredDisasterMsgDto {
    private DisasterMsg disasterMsg;
    private List<String> matchingLocations;
    private List<String> matchingKeywords;
    private List<String> matchingKeywordSentences;

}

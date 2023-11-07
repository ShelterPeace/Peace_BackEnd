package com.shelter.peace.emergencyMsg.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shelter.peace.shelterApi.service.dto.Row;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DisasterMsgDTO2 {
    @JsonProperty("head")
    private MsgHeaderDTO head;

    @JsonProperty("row")
    private List<MsgRowDTO> rows;  // 여러 개의 row 엘리먼트를 포함하는 리스트

}
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
public class DisasterMsgDTO {
    @JsonProperty("create_date")
    private String createDate;

    @JsonProperty("location_id")
    private int locationId;

    @JsonProperty("location_name")
    private String locationName;

    @JsonProperty("md101_sn")
    private int md101Sn;

    @JsonProperty("msg")
    private String message;

    @JsonProperty("send_platform")
    private String sendPlatform;

    @JsonProperty("row")
    private List<Row> row;

    // 추가: 필드를 JSON 문자열로 변환
    public String getRows() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this.row);
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // 또는 예외 처리 방식을 선택하세요.
            return null; // 예외 발생 시 기본값을 반환하거나 예외 처리 방식을 선택하세요.
        }
    }

}
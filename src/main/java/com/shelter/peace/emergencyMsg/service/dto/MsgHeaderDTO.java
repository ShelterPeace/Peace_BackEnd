package com.shelter.peace.emergencyMsg.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MsgHeaderDTO {
    @JsonProperty("totalCount")
    private int totalCount;

    @JsonProperty("numOfRows")
    private int numOfRows;

    @JsonProperty("pageNo")
    private int pageNo;

    @JsonProperty("type")
    private String type;

    @JsonProperty("RESULT")
    private MsgResultDTO result;

}

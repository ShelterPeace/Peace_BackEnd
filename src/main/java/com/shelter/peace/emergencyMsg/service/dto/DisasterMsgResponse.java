package com.shelter.peace.emergencyMsg.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class DisasterMsgResponse {
    @JsonProperty("DisasterMsg")
    private List<DisasterMsgData> disasterMsg;
}


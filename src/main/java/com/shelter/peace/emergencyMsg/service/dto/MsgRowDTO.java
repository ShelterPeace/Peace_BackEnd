package com.shelter.peace.emergencyMsg.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class MsgRowDTO {
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
}


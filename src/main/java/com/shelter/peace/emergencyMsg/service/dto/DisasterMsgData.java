package com.shelter.peace.emergencyMsg.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class DisasterMsgData {
    @JsonProperty("DisasterMsg")
    private List<DisasterMsg> disasterMsg;

    public List<DisasterMsg> getDisasterMsg() {
        return disasterMsg;
    }

    @Data
    @Getter
    @Setter
    public static class DisasterMsg {
        @JsonProperty("head")
        private List<Head> head;
        @JsonProperty("row")
        private List<Row> row;
    }

    @Data
    public static class Head {
        @JsonProperty("totalCount")
        private int totalCount;
        @JsonProperty("numOfRows")
        private String numOfRows;
        @JsonProperty("pageNo")
        private String pageNo;
        @JsonProperty("type")
        private String type;
        @JsonProperty("RESULT")
        private Result RESULT;
    }

    @Data
    public static class Result {
        @JsonProperty("resultCode")
        private String resultCode;
        @JsonProperty("resultMsg")
        private String resultMsg;
    }

    @Data
    public static class Row {
        @JsonProperty("create_date")
        private String createDate;
        @JsonProperty("location_id")
        private String locationId;
        @JsonProperty("location_name")
        private String locationName;
        @JsonProperty("md101_sn")
        private int md101Sn;
        @JsonProperty("msg")
        private String message;
        @JsonProperty("send_platform")
        private String sendPlatform;

    }


}

package com.shelter.peace.shelterApi.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
public class EarthquakeShelterResponseDTO {
    @JsonProperty("EarthquakeOutdoorsShelter")
    private List<EarthquakeOutdoorsShelter> earthquakeOutdoorsShelter;

    @Data
    public static class EarthquakeOutdoorsShelter {
        @JsonProperty("head")
        private List<Head> head;

        @JsonProperty("row")
        private List<EarthquakeShelterDTO> row;
    }

    @Data
    public static class Head {
        @JsonProperty("totalCount")
        private String totalCount;

        @JsonProperty("numOfRows")
        private String numOfRows;

        @JsonProperty("pageNo")
        private String pageNo;

        @JsonProperty("type")
        private String type;

        @JsonProperty("RESULT")
        private Result result;

        @Data
        public static class Result {
            @JsonProperty("resultCode")
            private String resultCode;

            @JsonProperty("resultMsg")
            private String resultMsg;
        }
    }
}
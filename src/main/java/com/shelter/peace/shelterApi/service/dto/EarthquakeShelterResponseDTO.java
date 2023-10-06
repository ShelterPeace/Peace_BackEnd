package com.shelter.peace.shelterApi.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

@Data
public class EarthquakeShelterResponseDTO {
    @JsonProperty("head")
    private Head head;

    @JsonProperty("row")
    private List<Row> rowList;

    @Data
    public static class Head {
        @JsonProperty("totalCount")
        private int totalCount;

        @JsonProperty("numOfRows")
        private int numOfRows;

        @JsonProperty("pageNo")
        private int pageNo;

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

    @Data
    public static class Row {
        @JsonProperty("arcd")
        private String arcd;

        @JsonProperty("acmdfclty_sn")
        private int acmdfcltySn;

        @JsonProperty("ctprvn_nm")
        private String ctprvnNm;

        @JsonProperty("sgg_nm")
        private String sggNm;

        @JsonProperty("vt_acmdfclty_nm")
        private String vtAcmdfcltyNm;

        @JsonProperty("rdnmadr_cd")
        private String rdnmadrCd;

        @JsonProperty("bdong_cd")
        private String bdongCd;

        @JsonProperty("hdong_cd")
        private String hdongCd;

        @JsonProperty("dtl_adres")
        private String dtlAdres;

        @JsonProperty("fclty_ar")
        private String fcltyAr;

        @JsonProperty("xcord")
        private String xcord;

        @JsonProperty("ycord")
        private String ycord;
    }
}
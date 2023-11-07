package com.shelter.peace.fireExtinguisher.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "TbGiEmerhydP")
@XmlAccessorType(XmlAccessType.FIELD)
public class FireExtinguisherDTO {
    @XmlElement(name = "list_total_count")
    private int listTotalCount;
    @XmlElement(name = "RESULT")
    private Result result;

    @XmlElement(name = "row")
    private List<Row> rows;

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Result {
        @XmlElement(name = "CODE")
        private String code;
        @XmlElement(name = "MESSAGE")
        private String message;
    }
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Row {
        @XmlElement(name = "OBJECTID")
        private int objectId; //연번

        @XmlElement(name = "EMERHYDSEQ")
        private long emerHydSeq; //비상소화장치일련번호

        @XmlElement(name = "GISID")
        private int gisId; //일련번호

        @XmlElement(name = "NAVI_X")
        private double naviX; //X좌표

        @XmlElement(name = "NAVI_Y")
        private double naviY; //Y좌표

        @XmlElement(name = "EMERHYD_NO")
        private String emerHydNo; //비상소화장치고유번호

        @XmlElement(name = "USE_YN_SE")
        private int useYnSe; //사용구분

        @XmlElement(name = "DELETE_AT")
        private String deleteAt; //삭제여부

        @XmlElement(name = "UPDT_DE")
        private String updtDe; //업데이트날짜


    }
}

package com.shelter.peace.fireExtinguisher.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "FirefgtFacltDevice")
@XmlAccessorType(XmlAccessType.FIELD)
public class GyeonggiFireExtDTO {
    @XmlElement(name = "list_total_count")
    private int listTotalCount;
    @XmlElement(name = "RESULT")
    private Result result;
    @XmlElement(name = "api_version")
    private String apiVersion;
    @XmlElement(name = "row")
    private List<Row> row;

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
        @XmlElement(name = "SIGUN_NM")
        private String sigunNm;
        @XmlElement(name = "FACLT_DEVICE_DETAIL_DIV_NM")
        private String facLtDeviceDetailDivNm;
        @XmlElement(name = "REFINE_WGS84_LAT")
        private double refineWgs84Lat;
        @XmlElement(name = "REFINE_WGS84_LOGT")
        private double refineWgs84Logt;
        @XmlElement(name = "FACLT_DEVICE_DIV_NM")
        private String facLtDeviceDivNm;
        @XmlElement(name = "JURISD_FIRESTTN_NM")
        private String jurisdFiresttnNm;
        @XmlElement(name = "JURISD_FIRESTTN_TELNO")
        private String jurisdFiresttnTelNo;
        @XmlElement(name = "FACLT_TYPE_CD")
        private int facLtTypeCd;
        @XmlElement(name = "DATA_STD_DE")
        private String dataStdDe;
        @XmlElement(name = "PIPNG_DEPH")
        private int pipngDeph;
        @XmlElement(name = "PIPNG_DMTR")
        private int pipngDmtr;
        @XmlElement(name = "PROTECT_OUTLN_EXTNO")
        private String protectOutlnExtno;
        @XmlElement(name = "USE_POSBL_YN")
        private String usePosblYn;
        @XmlElement(name = "INSTL_YY")
        private int instlYy;
        @XmlElement(name = "SIGUN_CD")
        private String sigunCd;
        @XmlElement(name = "SIDO_NM")
        private String sidoNm;
        @XmlElement(name = "FACLT_NO")
        private String facLtNo;
        @XmlElement(name = "REFINE_LOTNO_ADDR")
        private String refineLotnoAddr;
        @XmlElement(name = "REFINE_ROADNM_ADDR")
        private String refineRoadNmAddr;
        @XmlElement(name = "INST_NM")
        private String instNm;

    }
}

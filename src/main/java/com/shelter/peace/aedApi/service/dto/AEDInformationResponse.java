package com.shelter.peace.aedApi.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.*;
import java.util.List;

@Data
@NoArgsConstructor
@XmlRootElement(name = "response")
public class AEDInformationResponse {
    private Header header;
    private Body body;

    // 생성자, getter, setter 생략

    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Header {
        @XmlElement(name = "resultCode")
        private String resultCode;
        @XmlElement(name = "resultMsg")
        private String resultMsg;

        // 생성자, getter, setter 생략
    }
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Body {
        @XmlElementWrapper(name = "items")
        @XmlElement(name = "item")
        private List<Item> items;

        // 생성자, getter, setter 생략
    }
    @Data
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Item {
        private String buildAddress;
        private String buildPlace;
        private String clerkTel;
        private int cnt;
        private String friEndTme;
        private String friSttTme;
        private String manager;
        private String managerTel;
        private String mfg;
        private String model;
        private String monEndTme;
        private String monSttTme;
        private String org;
        private int rnum;
        private String sunFifYon;
        private String sunFrtYon;
        private String sunFurYon;
        private String sunScdYon;
        private String sunThiYon;
        private String thuEndTme;
        private String thuSttTme;
        private String tueEndTme;
        private String tueSttTme;
        private String wedEndTme;
        private String wedSttTme;
        private double wgs84Lat;
        private double wgs84Lon;
        private String zipcode1;
        private String zipcode2;
        private String holEndTme;
        private String holSttTme;
        private String satEndTme;
        private String satSttTme;
        private String sunEndTme;
        // 생성자, getter, setter 생략
    }
}

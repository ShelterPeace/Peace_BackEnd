package com.shelter.peace.weather.dto;

import com.shelter.peace.user.service.dto.UserDTO;
import com.shelter.peace.weather.entity.InterestArea;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class InterestAreaDTO {
    private Long id;
    private String area1Name;
    private double area1Lat;
    private double area1Lon;
    private String area2Name;
    private double area2Lat;
    private double area2Lon;
    // 유저 조인
    private long userNo;

    public InterestArea DTOTOEntity() {
        return InterestArea.builder()
                .id(this.id)
                .area1Name(this.area1Name)
                .area1Lat(this.area1Lat)
                .area1Lon(this.area1Lon)
                .area2Name(this.area2Name)
                .area2Lat(this.area2Lat)
                .area2Lon(this.area2Lon)
                .userNo(this.userNo)
                .build();
    }
}

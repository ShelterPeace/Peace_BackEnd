package com.shelter.peace.weather.entity;

import com.shelter.peace.user.entity.User;
import com.shelter.peace.weather.dto.InterestAreaDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "INTEREST_AREA")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterestArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AREA_NO") //
    private Long id;

    private String area1Name;
    private double area1Lat;
    private double area1Lon;

    private String area2Name;
    private double area2Lat;
    private double area2Lon;

    // 유저 조인
    @Column(name = "USER_NO", nullable = false, unique = true)
    private long userNo;

    public InterestAreaDTO EntityTODTO() {
        return InterestAreaDTO.builder()
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

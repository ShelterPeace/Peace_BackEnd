package com.shelter.peace.emergencyMsg.entity;

import com.shelter.peace.shelterApi.service.dto.Row;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Data
@Getter
@Setter
public class DisasterMsg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String createDate;
    @Column(nullable = true)
    private String locationId;
    @Column(nullable = true)
    private String locationName;
    @Column(nullable = true)
    private int md101Sn;
    @Column(nullable = true)
    private String message;
    @Column(nullable = true)
    private String sendPlatform;
//
//    // JSON 문자열로 저장
//    @Column(name = "rowData", nullable = true)
//    private String row;
}

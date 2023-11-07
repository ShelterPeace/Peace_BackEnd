package com.shelter.peace.fireExtinguisher.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
public class FireExtinguisher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private int objectId; //연번
    @Column(nullable = true)
    private long emerHydSeq; //비상소화장치일련번호
    @Column(nullable = true)
    private int gisId; //일련번호
    @Column(nullable = true)
    private double naviX; //X좌표
    @Column(nullable = true)
    private double naviY; //Y좌표
    @Column(nullable = true)
    private String emerHydNo; //비상소화장치고유번호
    @Column(nullable = true)
    private int useYnSe; //사용구분
    @Column(nullable = true)
    private String deleteAt; //삭제여부
    @Column(nullable = true)
    private String updtDe; //업데이트날짜
}

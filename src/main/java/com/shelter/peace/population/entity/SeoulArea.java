package com.shelter.peace.population.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "SEOUL_AREA")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeoulArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private int no;
    private String areaCd;
    private String areaNm;

}

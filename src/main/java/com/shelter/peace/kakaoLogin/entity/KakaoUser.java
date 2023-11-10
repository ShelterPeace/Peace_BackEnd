package com.shelter.peace.kakaoLogin.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "KAKAO_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KakaoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;
}

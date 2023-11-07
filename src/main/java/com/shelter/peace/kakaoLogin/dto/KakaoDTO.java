package com.shelter.peace.kakaoLogin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoDTO {
    private Long id;
    private String nickname;
    private String email;

    public KakaoDTO(Long id, String nickname, String email) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
    }
}

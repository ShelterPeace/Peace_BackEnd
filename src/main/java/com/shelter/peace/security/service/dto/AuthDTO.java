package com.shelter.peace.security.service.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDTO {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDTO {
        @NotEmpty(message = "아이디 값이 비어있습니다.")
        private String userId;
        @NotEmpty(message = "비밀번호 값이 누락되었습니다.")
        private String userPwd;

        @Builder
        public LoginDTO(String userId, String userPwd) {
            this.userId = userId;
            this.userPwd = userPwd;
        }
    }


    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class TokenDTO {
        private String accessToken;
        private String refreshToken;

        public TokenDTO(String accessToken, String refreshToken) {
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}

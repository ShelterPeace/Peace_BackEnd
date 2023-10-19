package com.shelter.peace.security.service.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthDTO {
    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class LoginDTO {
        private String userId;
        private String userPwd;

        @Builder
        public LoginDTO(String userId, String userPwd) {
            this.userId = userId;
            this.userPwd = userPwd;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignupDTO {
        private String userId;
        private String userPwd;

        @Builder
        public SignupDTO(String userId, String userPwd) {
            this.userId = userId;
            this.userPwd = userPwd;
        }

        public static SignupDTO encodePassword(SignupDTO signupDTO, String encodedPassword) {
            SignupDTO newSignupDTO = new SignupDTO();
            newSignupDTO.userId = signupDTO.getUserId();
            newSignupDTO.userPwd = signupDTO.getUserPwd();

            return newSignupDTO;
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

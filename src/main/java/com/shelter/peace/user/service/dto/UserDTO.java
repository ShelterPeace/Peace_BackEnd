package com.shelter.peace.user.service.dto;

import com.shelter.peace.security.service.dto.Role;
import com.shelter.peace.user.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
@Setter
public class UserDTO {
    private Long id;
    private String userId;
    private String userPwd;
    private String userName;
    private String userAddress;
    private String userEmail;
    //    private String userPhone;
//    private String userPhone;
//    private LocalDate userBirth;
//    private boolean userSvc;
//    private boolean userPs;
//    private boolean userLoc;
//    private LocalDate userRegDate;
    @Enumerated(EnumType.STRING)
    private Role role;

    public User DTOTOEntity() {
        return User.builder()
                .id(this.id)
                .userId(this.userId)
                .userPwd(this.userPwd)
                .userName(this.userName)
                .userAddress(this.userAddress)
                .userEmail(this.userEmail)
//                .userPhone(this.userPhone)
//                .userBirth(this.userBirth)
//                .userSvc(this.userSvc)
//                .userPs(this.userPs)
//                .userLoc(this.userLoc)
//                .userRegDate(this.userRegDate)
                .role(this.role)
                .build();
    }

    @Getter
    @Setter
    public static class PasswordDTO {
        private String newPassword;
        private String newPasswordConfirm;
    }

}




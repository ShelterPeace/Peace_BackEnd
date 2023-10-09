package com.shelter.peace.user.service.dto;

import com.shelter.peace.user.entity.Role;
import com.shelter.peace.user.entity.User;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
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
//    private String userName;
//    private String userPhone;
//    private String userAddress;
//    private LocalDate userBirth;
//    private String userEmail;
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
//                .userName(this.userName)
//                .userPhone(this.userPhone)
//                .userAddress(this.userAddress)
//                .userBirth(this.userBirth)
//                .userEmail(this.userEmail)
//                .userSvc(this.userSvc)
//                .userPs(this.userPs)
//                .userLoc(this.userLoc)
//                .userRegDate(this.userRegDate)
                .role(this.role)
                .build();
    }
}

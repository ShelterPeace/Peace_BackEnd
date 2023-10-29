package com.shelter.peace.user.entity;

import com.shelter.peace.security.service.dto.Role;
import com.shelter.peace.user.service.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO") //
    private Long id;

    @Column(name = "USER_ID", nullable = false, unique = true)
    private String userId;

    @Column(name = "USER_PWD", nullable = false)
    private String userPwd;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "USER_ADDRESS", nullable = false)
    private String userAddress;

    @Column(name = "USER_EMAIL", nullable = true, unique = true)
    private String userEmail = "";

//    @Column(name = "USER_PHONE", nullable = false)
//    private String userPhone;
//
//    @Column(name = "USER_BIRTH", nullable = true)
//    private LocalDate userBirth;
//
//    @Column(name = "USER_SVC_YN", nullable = false)
//    private boolean userSvc;
//
//    @Column(name = "USER_PS_YN", nullable = false)
//    private boolean userPs;
//
//    @Column(name = "USER_LOC_YN", nullable = false)
//    private boolean userLoc;
//
//    @Column(name = "USER_REG_DATE")
//    private LocalDate userRegDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private Role role;

    public UserDTO EntityTODTO() {
        return UserDTO.builder()
                .id(this.id)
                .userId(this.userId)
                .userPwd(this.userPwd)
                .userName(this.userName)
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

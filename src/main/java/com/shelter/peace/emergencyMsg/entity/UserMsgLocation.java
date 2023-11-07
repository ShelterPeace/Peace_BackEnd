package com.shelter.peace.emergencyMsg.entity;

import com.shelter.peace.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "USER_Msg_LOCATION")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMsgLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_NO") //
    private Long id;

    @Column(name = "LOCATION_NAME", nullable = false)
    private String locationName;

    // User 엔티티와의 관계 설정 (ManyToOne)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    // User 엔티티에 대한 참조 설정을 위한 메서드
    public void setUser(User user) {
        this.user = user;
    }
}

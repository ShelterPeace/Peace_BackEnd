package com.shelter.peace.emergencyMsg.entity;

import com.shelter.peace.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserNotification {
    //사용자 ID와 알림 메시지
    @Id
    @GeneratedValue
    private Long id;

    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}

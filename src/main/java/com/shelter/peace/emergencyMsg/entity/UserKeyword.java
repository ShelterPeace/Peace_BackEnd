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
public class UserKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "keyword") // 최대 길이 제한 추가
    private String keyword; // 사용자 지정 키워드

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}

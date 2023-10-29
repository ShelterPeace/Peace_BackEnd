package com.shelter.peace.inquiry.entity;

import com.shelter.peace.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnABoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QNANO")
    private Long qnANo;

    @Column(name = "QNATitle")
    private String qnATitle;

    @Column(name = "QNAContent")
    private String qnAContent;

    @Column(name = "QNAWriter")
    private String qnAWriter;

    @Column(name = "QNA_CNT", nullable = false)
    private int qnACnt = 0;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate; // 작성 날짜

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setUserDetails(User userDetails) {
        this.qnAWriter = userDetails.getUserId(); // 또는 필요한 다른 정보를 설정할 수 있습니다
    }

    public void setUser(String currentUser) {
    }
}


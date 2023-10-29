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
public class QnAReply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate; // 댓글 작성 날짜 및 시간

    @ManyToOne
    @JoinColumn(name = "qnANO")
    private QnABoard qnABoard; // QnABoard와의 양방향 관계

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    //댓글이나 답변과 같이 어떤 게시물이나 주요 엔티티와 연관된 엔티티를 조회할 때 사용
    public Long getQnANo() {
        if (qnABoard != null) {
            return qnABoard.getQnANo();
        }
        return null; // 게시물이 연결되어 있지 않다면 null을 반환하도록 설정
    }
    public QnAReply(QnABoard qnABoard, User user, String content) {
        this.qnABoard = qnABoard;
        this.user = user;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }

}

package com.shelter.peace.inquiry.entity;

import com.shelter.peace.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnAReply {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    private String content;

    @JsonIgnore
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate; // 댓글 작성 날짜 및 시간

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "QNANO")
    private QnABoard qnABoard; // QnABoard와의 양방향 관계

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private User user;

    //댓글이나 답변과 같이 어떤 게시물이나 주요 엔티티와 연관된 엔티티를 조회할 때 사용
    public Long getQnANo() {
        if (qnABoard != null) {
            return qnABoard.getQnANo();
        }
        return -1L; // 게시물이 연결되어 있지 않다면 -1 또는 다른 기본값을 반환하도록 설정
    }
    public QnAReply(QnABoard qnABoard, User user, String content) {
        this.qnABoard = qnABoard;
        this.user = user;
        this.content = content;
        this.createdDate = LocalDateTime.now();
    }

}

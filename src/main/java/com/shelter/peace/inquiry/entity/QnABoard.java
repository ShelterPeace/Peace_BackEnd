package com.shelter.peace.inquiry.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.shelter.peace.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QnABoard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "QNANO")
    private Long qnANo;

    @JsonIgnore
    @Column(name = "QNATitle")
    private String qnATitle;

    @JsonIgnore
    @Column(name = "QNAContent")
    private String qnAContent;

    @JsonIgnore
    @Column(name = "QNAWriter")
    private String qnAWriter;

    @JsonIgnore
    @Column(name = "QNA_CNT", nullable = false)
    private int qnACnt = 0;

    @JsonIgnore
    @Column(name = "CREATED_DATE")
    private LocalDateTime createdDate; // 작성 날짜

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "USER_ID")
    @JsonManagedReference
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "qnABoard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QnAReply> qnAReplies;

    public QnABoard(String qnATitle, String qnAContent, User user) {
        this.qnATitle = qnATitle;
        this.qnAContent = qnAContent;
        this.user = user;
    }

    // 게시글 상세 조회 시 조회수 증가 메서드
    public void incrementQnACnt() {
        this.qnACnt++;
    }
}


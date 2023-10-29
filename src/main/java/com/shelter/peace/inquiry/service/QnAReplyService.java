package com.shelter.peace.inquiry.service;

import com.shelter.peace.inquiry.entity.QnABoard;
import com.shelter.peace.inquiry.entity.QnAReply;
import com.shelter.peace.inquiry.repository.QnABoardRepository;
import com.shelter.peace.inquiry.repository.QnAReplyRepository;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class QnAReplyService {

    private final QnAReplyRepository qnAReplyRepository;
    private final QnABoardRepository qnABoardRepository;

    @Autowired
    public QnAReplyService(QnAReplyRepository qnAReplyRepository, QnABoardRepository qnABoardRepository) {
        this.qnAReplyRepository = qnAReplyRepository;
        this.qnABoardRepository = qnABoardRepository;
    }

    @Transactional
    public QnAReply createQnAReply(QnAReply qnAReply, UserDetailsImpl userDetails) {
        // 댓글 생성 시, 해당 게시물의 ID를 지정하여 댓글을 연결합니다.
        Long qnANo = qnAReply.getQnANo();
        QnABoard qnABoard = qnABoardRepository.findById(qnANo).orElse(null);

        if (qnABoard == null) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다.");
        }

        // 댓글 작성자 설정
        qnAReply.setUser(userDetails.getUsername());

        // 댓글 생성 시간 설정
        qnAReply.setCreatedDate(LocalDateTime.now());

        // 댓글 저장
        return qnAReplyRepository.save(qnAReply);
    }

}
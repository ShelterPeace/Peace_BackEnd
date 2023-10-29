package com.shelter.peace.inquiry.service;

import com.shelter.peace.inquiry.entity.QnABoard;
import com.shelter.peace.inquiry.entity.QnAReply;
import com.shelter.peace.inquiry.repository.QnABoardRepository;
import com.shelter.peace.inquiry.repository.QnAReplyRepository;
import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class QnAReplyService {

    private final QnAReplyRepository qnAReplyRepository;
    private final QnABoardRepository qnABoardRepository;

    @Autowired
    public QnAReplyService(QnAReplyRepository qnAReplyRepository, QnABoardRepository qnABoardRepository) {
        this.qnAReplyRepository = qnAReplyRepository;
        this.qnABoardRepository = qnABoardRepository;
    }

    //댓글 작성
    @Transactional
    public QnAReply createQnAReply(QnAReply qnAReply, UserDetailsImpl userDetails) {
        // 댓글 생성 시, 해당 게시물의 ID를 지정하여 댓글을 연결
        Long qnANo = qnAReply.getQnANo();
        QnABoard qnABoard = qnABoardRepository.findById(qnANo).orElse(null);

        if (qnABoard == null) {
            throw new IllegalArgumentException("게시물을 찾을 수 없습니다.");
        }
        User user = userDetails.getUser();
        qnAReply.setUser(user);
        qnAReply.setCreatedDate(LocalDateTime.now());

        return qnAReplyRepository.save(qnAReply);
    }

    //replyId에 해당하는 댓글을 검색하고 해당 댓글 객체를 반환
    public QnAReply getQnAReplyById(Long replyId) {
        // 댓글 ID로 데이터베이스에서 댓글을 조회
        Optional<QnAReply> optionalReply = qnAReplyRepository.findById(replyId);
        return optionalReply.orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }

    // 답변 수정
    public void updateQnAReply(UserDetailsImpl userDetails, Long replyId, String updatedContent) {
        QnAReply existingReply = getQnAReplyById(replyId);

        if (existingReply == null) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
        if (!userDetails.getUser().getId().equals(existingReply.getUser().getId())) {
            throw new IllegalArgumentException("댓글을 수정할 권한이 없습니다.");
        }

        // 업데이트된 내용이 있을 경우에만 업데이트
        if (!existingReply.getContent().equals(updatedContent)) {
            existingReply.setContent(updatedContent);
            qnAReplyRepository.save(existingReply);
        }
    }


    // 답변 삭제
    public void deleteQnAReply(UserDetailsImpl userDetails, Long replyId) {
        // 댓글 ID로 댓글을 찾습니다.
        QnAReply existingReply = getQnAReplyById(replyId);
        if (existingReply == null) {
            throw new IllegalArgumentException("댓글을 찾을 수 없습니다.");
        }
        if (!userDetails.getUser().getId().equals(existingReply.getUser().getId())) {
            throw new IllegalArgumentException("댓글을 삭제할 권한이 없습니다.");
        }

        qnAReplyRepository.delete(existingReply);
    }

}
package com.shelter.peace.inquiry.controller;

import com.shelter.peace.inquiry.entity.QnABoard;
import com.shelter.peace.inquiry.entity.QnAReply;
import com.shelter.peace.inquiry.repository.QnABoardRepository;
import com.shelter.peace.inquiry.service.QnAReplyService;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/reply")
public class QnAAdminController {

    private final QnAReplyService qnAReplyService;
    private final QnABoardRepository qnABoardRepository;

    public QnAAdminController(QnAReplyService qnAReplyService, QnABoardRepository qnABoardRepository) {
        this.qnAReplyService = qnAReplyService;
        this.qnABoardRepository = qnABoardRepository;
    }

    // 답변 작성 (현재는 누구나 가능 추후 관리자만 작성할 수 있도록 변경 예정)
    // 특정 게시물에 달리도록 수정
    @PostMapping("/answer")
    public ResponseEntity<String> createQnAReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody QnAReply qnAReply) {
        Long qnANo = qnAReply.getQnANo(); // 요청에서 게시물 ID 추출
        QnABoard qnABoard = qnABoardRepository.findById(qnANo).orElse(null);

        if (qnABoard == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("게시물을 찾을 수 없습니다.");
        }

        qnAReply.setQnABoard(qnABoard); // 댓글을 특정 게시물에 연결
        qnAReply.setCreatedDate(LocalDateTime.now());

        QnAReply createdReply = qnAReplyService.createQnAReply(qnAReply, userDetails);
        if (createdReply != null) {
            return ResponseEntity.ok("답변이 작성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 작성에 실패하였습니다.");
        }
    }

    // 답변 수정
    @PutMapping("/{replyId}")
    public ResponseEntity<String> updateQnAReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long replyId, @RequestBody String updatedContent) {
        try {
            qnAReplyService.updateQnAReply(userDetails, replyId, updatedContent);
            return ResponseEntity.ok("댓글이 수정되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 댓글 삭제
    @DeleteMapping("/{replyId}")
    public ResponseEntity<String> deleteQnAReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long replyId) {
        try {
            qnAReplyService.deleteQnAReply(userDetails, replyId);
            return ResponseEntity.ok("댓글이 삭제되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}

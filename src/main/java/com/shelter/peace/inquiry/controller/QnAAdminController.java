package com.shelter.peace.inquiry.controller;

import com.shelter.peace.inquiry.entity.QnAReply;
import com.shelter.peace.inquiry.service.QnAReplyService;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class QnAAdminController {

    private final QnAReplyService qnAReplyService;

    public QnAAdminController(QnAReplyService qnAReplyService) {
        this.qnAReplyService = qnAReplyService;
    }

    // 답변 작성 (현재는 누구나 가능 추후 관리자만 작성할 수 있도록 변경 예정)
    @PostMapping("/reply")
    public ResponseEntity<String> createQnAReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody QnAReply qnAReply) {
        // 이미 userDetails를 받고 있으므로 userDetails를 그대로 사용
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

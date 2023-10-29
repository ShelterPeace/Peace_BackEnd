package com.shelter.peace.inquiry.controller;

import com.shelter.peace.inquiry.entity.QnAReply;
import com.shelter.peace.inquiry.service.QnAReplyService;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QnAAdminController {

    private final QnAReplyService qnAReplyService;

    public QnAAdminController(QnAReplyService qnAReplyService) {
        this.qnAReplyService = qnAReplyService;
    }

    // 답변 작성 (현재는 누구나 가능 추후 관리자만 작성할 수 있도록 변경 예정)
    @PostMapping("/reply")
    public ResponseEntity<String> createQnAReply(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody QnAReply qnAReply) {
        qnAReply.setUser(userDetails.getUsername());
        QnAReply createdReply = qnAReplyService.createQnAReply(qnAReply, userDetails);
        if (createdReply != null) {
            return ResponseEntity.ok("답변이 작성되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("답변 작성에 실패하였습니다.");
        }
    }
}

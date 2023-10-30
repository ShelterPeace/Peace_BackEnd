package com.shelter.peace.inquiry.controller;

import com.shelter.peace.inquiry.entity.QnABoard;
import com.shelter.peace.inquiry.repository.QnABoardRepository;
import com.shelter.peace.inquiry.service.QnABoardService;
import com.shelter.peace.inquiry.service.dto.QnABoardDTO;
import com.shelter.peace.user.entity.UserDetailsImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/QnA")
public class QnAWriterController {

    private final QnABoardService qnABoardService;
    private final QnABoardRepository qnABoardRepository;
    public QnAWriterController(QnABoardService qnABoardService, QnABoardRepository qnABoardRepository) {
        this.qnABoardService = qnABoardService;

        this.qnABoardRepository = qnABoardRepository;
    }

    // 게시물 작성
    @PostMapping("/write")
    public ResponseEntity<?> createQnABoard(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody QnABoard qnABoard) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }

        System.out.println("sssss" + qnABoard);

        if (qnABoard.getQnATitle() == null || qnABoard.getQnAContent() == null ||
                qnABoard.getQnATitle().trim().isEmpty() || qnABoard.getQnAContent().trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("제목과 내용은 필수 입력 항목입니다.");
        }

        qnABoardService.createQnABoard(qnABoard, userDetails.getUsername()); // 사용자 ID를 가져와서 게시물 작성 서비스 호출
        return ResponseEntity.ok().build();
    }

    // 게시물 목록 조회
    @GetMapping("/list")
    public ResponseEntity<List<QnABoardDTO>> getAllQnABoards() {
        List<Object[]> results = qnABoardRepository.findQnABoardAdditionalInfo();
        List<QnABoardDTO> qnABoards = new ArrayList<>();

        for (Object[] result : results) {
            QnABoardDTO dto = new QnABoardDTO();
            dto.setQnANo((Long) result[0]);
            dto.setQnATitle((String) result[1]);
            dto.setQnACnt((int) result[2]);
            dto.setCreatedDate((LocalDateTime) result[3]);
            dto.setQnAWriter((String) result[4]);

            qnABoards.add(dto);
        }

        return ResponseEntity.ok(qnABoards);
    }

    //게시글 수정
    @PutMapping("/update/{qnANo}")
    public ResponseEntity<String> updateQnABoard(
            @PathVariable Long qnANo,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody QnABoard updatedQnABoard) {
        String currentUser = userDetails.getUsername(); // 현재 로그인한 사용자 설정

        QnABoard updatedBoard = qnABoardService.updateQnABoard(qnANo, updatedQnABoard, currentUser);

        if (updatedBoard != null) {
            return ResponseEntity.ok("게시글 수정이 완료되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("게시글 수정에 실패하였습니다.");
        }
    }

    // 게시물 삭제
    @DeleteMapping("/delete/{qnANo}")
    public ResponseEntity<String> deleteQnABoard(
            @PathVariable Long qnANo,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        String currentUser = userDetails.getUsername(); // 현재 로그인한 사용자 설정

        try {
            qnABoardService.deleteQnABoard(qnANo, currentUser);
            return ResponseEntity.ok("게시물 삭제가 완료되었습니다.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    // 게시물 ID로 게시물 조회
    @GetMapping("/searchById/{qnANo}")
    public ResponseEntity<QnABoard> searchQnABoardById(@PathVariable Long qnANo) {
        QnABoard qnABoard = qnABoardService.getQnABoardByNo(qnANo);

        if (qnABoard == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(qnABoard);
    }

    // 게시물 제목으로 게시물 조회(부분 문자열 일치 검색 가능)
    @GetMapping("/searchByTitle")
    public ResponseEntity<Page<QnABoard>> searchQnABoardsByTitle(@RequestParam String qnATitle,
                                                                 @PageableDefault(size = 15, sort = "createdDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<QnABoard> qnABoards = qnABoardService.getQnABoardsByTitle(qnATitle, pageable);

        if (qnABoards.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(qnABoards);
    }

    // 게시글 상세 조회
    @GetMapping("/detail/{qnANo}")
    public ResponseEntity<QnABoard> getQnABoardDetail(@PathVariable Long qnANo) {
        QnABoard qnABoard = qnABoardService.getQnABoardByNo(qnANo);

        if (qnABoard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(qnABoard);
    }
}

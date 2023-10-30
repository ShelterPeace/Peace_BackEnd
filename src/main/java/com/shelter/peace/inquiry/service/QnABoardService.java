package com.shelter.peace.inquiry.service;

import com.shelter.peace.inquiry.entity.QnABoard;
import com.shelter.peace.inquiry.repository.QnABoardRepository;
import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.entity.UserDetailsImpl;
import com.shelter.peace.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QnABoardService {

    private final QnABoardRepository qnABoardRepository;
    private final UserService userService;

    //로그인한 유저 본인의 글만 보이도록
    @Transactional
    public Page<QnABoard> getQnABoardsByUser(String userId, Pageable pageable) {
        return qnABoardRepository.findByUserUserId(userId, pageable);
    }

    // QnA 작성
    @Transactional
    public QnABoard createQnABoard(QnABoard qnABoard, String currentUser) {
        // 제목과 내용이 비어 있는지 확인
        if (qnABoard.getQnATitle() == null || qnABoard.getQnAContent() == null ||
                qnABoard.getQnATitle().trim().isEmpty() || qnABoard.getQnAContent().trim().isEmpty()) {
            throw new IllegalArgumentException("제목과 내용은 필수 입력 항목입니다.");
        }

        User user = userService.getUserByUserId(currentUser);

        qnABoard.setUser(user);
        qnABoard.setQnAWriter(currentUser); // 사용자 정보 설정
        qnABoard.setQnACnt(1); // 조회수 초기화
        qnABoard.setCreatedDate(LocalDateTime.now()); // 작성 날짜 설정

        return qnABoardRepository.save(qnABoard);
    }

//    @Transactional
//    public QnABoard createQnABoard(User user, String title, String content) {
//        if (title == null || content == null || title.trim().isEmpty() || content.trim().isEmpty()) {
//            throw new IllegalArgumentException("제목과 내용은 필수 입력 항목입니다.");
//        }
//
//        QnABoard qnABoard = new QnABoard();
//        qnABoard.setUser(user);
//        qnABoard.setQnAWriter(user.getUserName());
//        qnABoard.setQnACnt(1);
//        qnABoard.setCreatedDate(LocalDateTime.now());
//        qnABoard.setQnATitle(title);
//        qnABoard.setQnAContent(content);
//
//        return qnABoardRepository.save(qnABoard);
//    }

    // QnA 게시글 수정
    @Transactional
    public QnABoard updateQnABoard(Long qnANo, QnABoard updatedQnABoard, String currentUser) {
        // 게시물 ID로 기존 게시물을 가져옵니다.
        QnABoard existingQnABoard = qnABoardRepository.findById(qnANo).orElse(null);

        // 게시물이 존재하지 않을 경우 예외 처리
        if (existingQnABoard == null) {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        }
        // 현재 로그인한 사용자와 게시물 작성자를 비교하여 게시물을 수정할 권한 확인
        if (!currentUser.equals(existingQnABoard.getQnAWriter())) {
            throw new IllegalArgumentException("게시물을 수정할 권한이 없습니다.");
        }
        // 업데이트된 내용이 있을 경우에만 업데이트
        String updatedTitle = updatedQnABoard.getQnATitle();
        String updatedContent = updatedQnABoard.getQnAContent();

        System.out.println("existingQnABoard: " + existingQnABoard);
        System.out.println("updatedTitle: " + updatedTitle);
        System.out.println("updatedContent: " + updatedContent);

        if (!existingQnABoard.getQnATitle().equalsIgnoreCase(updatedTitle) || !existingQnABoard.getQnAContent().equalsIgnoreCase(updatedContent)) {
            existingQnABoard.setQnATitle(updatedTitle);
            existingQnABoard.setQnAContent(updatedContent);
            return qnABoardRepository.save(existingQnABoard);
        }
        return existingQnABoard;
    }

    // QnA 게시글 삭제
    @Transactional
    public void deleteQnABoard(Long qnANo, String currentUser) {
        QnABoard qnABoard = qnABoardRepository.findById(qnANo).orElse(null);
        // 게시물이 존재하지 않을 경우 예외 처리
        if (qnABoard == null) {
            throw new IllegalArgumentException("게시물이 존재하지 않습니다.");
        }
        // 현재 로그인한 사용자와 게시물 작성자를 비교하여 게시물 삭제할 권한 확인
        if (!currentUser.equals(qnABoard.getQnAWriter())) {
            throw new IllegalArgumentException("게시물을 삭제할 권한이 없습니다.");
        }
        qnABoardRepository.deleteById(qnANo);
    }



    // QnA 게시물 ID로 게시물 조회
    @Transactional
    public QnABoard getQnABoardByNo(Long qnANo) {
        return qnABoardRepository.findById(qnANo).orElse(null);
    }
    // QnA 게시물 qnATitle로 게시물 조회(제목에서 부분 문자열 일치 검색이 가능)
    @Transactional
    public Page<QnABoard> getQnABoardsByTitle(String qnATitle, Pageable pageable) {
        return qnABoardRepository.findByQnATitleContainingIgnoreCaseOrderByCreatedDateDesc(qnATitle, pageable);
    }
}

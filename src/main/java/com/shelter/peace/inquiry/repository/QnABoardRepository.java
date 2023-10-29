package com.shelter.peace.inquiry.repository;

import com.shelter.peace.inquiry.entity.QnABoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface QnABoardRepository extends JpaRepository<QnABoard, Long> {
    QnABoard save(QnABoard qnABoard);

    Optional<QnABoard> findById(Long qnANo);

    void deleteById(Long qnANo);

    Page<QnABoard> findByUserUserId(String userId, Pageable pageable);

    Page<QnABoard> findByQnATitleContainingIgnoreCaseOrderByCreatedDateDesc(String qnATitle, Pageable pageable);
}

package com.shelter.peace.inquiry.repository;


import com.shelter.peace.inquiry.entity.QnAReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QnAReplyRepository extends JpaRepository<QnAReply, Long> {
    QnAReply save(QnAReply qnAReply);
}

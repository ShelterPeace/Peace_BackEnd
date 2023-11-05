package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.entity.UserKeyword;
import com.shelter.peace.user.entity.User;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserKeywordService {
    private final EntityManager entityManager;
    private final KeywordService keywordService;

    @Autowired
    public UserKeywordService(EntityManager entityManager, KeywordService keywordService) {
        this.entityManager = entityManager;
        this.keywordService = keywordService;
    }

    //사용자 지정 키워드 저장하기
    @Transactional
    public String saveUserKeyword(String userId, String keyword) {
        User user = entityManager.find(User.class, userId); // 사용자 엔티티를 먼저 가져옵니다.

        if (user != null) {
            // KeywordService를 사용하여 키워드 유효성 검사
            if (keywordService.isValidKeyword(keyword)) {
                UserKeyword userKeyword = new UserKeyword();
                userKeyword.setKeyword(keyword);
                userKeyword.setUser(user);
                entityManager.persist(userKeyword);
                return "키워드 저장이 완료되었습니다";
            } else {
                return "존재하지 않는 키워드입니다";
            }
        }

        return "사용자를 찾을 수 없습니다"; // 예외 처리 또는 메시지
    }

    // 사용자 지정 키워드 삭제하기
    @Transactional
    public void deleteUserKeyword(String userId, String keyword) {
        User user = entityManager.find(User.class, userId); // 사용자 엔티티를 먼저 가져옵니다.

        if (user != null) {
            // 해당 키워드를 찾아서 삭제
            UserKeyword userKeyword = entityManager.createQuery("SELECT uk FROM UserKeyword uk WHERE uk.user = :user AND uk.keyword = :keyword", UserKeyword.class)
                    .setParameter("user", user)
                    .setParameter("keyword", keyword)
                    .getSingleResult();

            if (userKeyword != null) {
                entityManager.remove(userKeyword);
            }
        }
    }
}

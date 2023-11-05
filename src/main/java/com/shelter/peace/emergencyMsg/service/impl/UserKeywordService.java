package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.entity.UserKeyword;
import com.shelter.peace.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                    .setParameter("userId", userId)
                    .getSingleResult();

            // 이미 동일한 키워드가 저장되어 있는지 확인
            UserKeyword existingUserKeyword = entityManager.createQuery("SELECT uk FROM UserKeyword uk WHERE uk.user = :user AND uk.keyword = :keyword", UserKeyword.class)
                    .setParameter("user", user)
                    .setParameter("keyword", keyword)
                    .getResultList().stream().findFirst().orElse(null);

            if (existingUserKeyword != null) {
                return "이미 추가된 키워드입니다.";
            }

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
        } catch (NoResultException e) {
            return "사용자를 찾을 수 없습니다";
        }
    }

    // 사용자 지정 키워드 삭제하기
    @Transactional
    public void deleteUserKeyword(String userId, String keyword) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();

        if (user != null) {
            // 해당 키워드를 찾아서 삭제
            List<UserKeyword> userKeywords = entityManager.createQuery("SELECT uk FROM UserKeyword uk WHERE uk.user = :user AND uk.keyword = :keyword", UserKeyword.class)
                    .setParameter("user", user)
                    .setParameter("keyword", keyword)
                    .getResultList();

            if (!userKeywords.isEmpty()) {
                for (UserKeyword userKeyword : userKeywords) {
                    entityManager.remove(userKeyword);
                }
            } else {
                throw new NoResultException("삭제할 키워드가 없습니다.");
            }
        }
    }
}

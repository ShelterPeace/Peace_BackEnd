package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.entity.UserKeyword;
import com.shelter.peace.emergencyMsg.entity.UserNotification;
import com.shelter.peace.emergencyMsg.repository.UserNotificationRepository;
import com.shelter.peace.emergencyMsg.service.NotificationService;
import com.shelter.peace.user.entity.User;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserNotificationService implements NotificationService {
    private static final Logger log = LoggerFactory.getLogger(UserNotificationService.class);
    private final UserNotificationRepository userNotificationRepository;
    private final EntityManager entityManager;

    @Autowired
    public UserNotificationService(UserNotificationRepository userNotificationRepository, EntityManager entityManager) {
        this.userNotificationRepository = userNotificationRepository;
        this.entityManager = entityManager;
    }
    @Override
    @Transactional
    public void sendNotification(String userId, String message) {
        // 사용자 설정 키워드 작동 확인
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();

        List<UserKeyword> userKeywords = user.getUserKeywords(); // 사용자가 가진 모든 키워드를 가져옵니다.
        boolean keywordFound = false;
        StringBuilder foundKeywords = new StringBuilder();

        for (UserKeyword userKeyword : userKeywords) {
            String keyword = userKeyword.getKeyword();
            if (message.contains(keyword)) {
                keywordFound = true;
                foundKeywords.append(keyword).append(" ");
            }
        }

        if (!keywordFound) {
            message = "키워드에 해당하지 않습니다";
        } else {
            message = "발견된 키워드: " + foundKeywords.toString();
        }

        log.info("사용자이름 {}: {}", userId, message);

        // 알림 내용을 데이터베이스에 저장
        saveNotification(user.getUserId(), message);
    }

    //사용자에게 알림을 보낼 때마다 그 알림 내용이 데이터베이스에 저장
    @Transactional
    public void saveNotification(String userId, String message) {
        User user = entityManager.createQuery("SELECT u FROM User u WHERE u.userId = :userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();

        UserNotification notification = new UserNotification();
        notification.setUser(user);
        notification.setMessage(message);

        userNotificationRepository.save(notification);
    }

    public List<UserNotification> getNotificationsByUserId(String userId) {
        return userNotificationRepository.findByUser_UserId(userId);
    }

}

package com.shelter.peace;

import com.shelter.peace.emergencyMsg.entity.UserNotification;
import com.shelter.peace.emergencyMsg.repository.UserNotificationRepository;
import com.shelter.peace.emergencyMsg.service.impl.UserNotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserNotificationServiceTest {

    @Autowired
    private UserNotificationService userNotificationService;

    @MockBean
    private UserNotificationRepository userNotificationRepository;

    @Test
    public void testSendNotification_KeywordFound() {
        String userId = "test1";
        String keyword = "산사태"; // 테스트할 키워드
        String message = keyword + "이다두두두두ㅜㄷ"; // 키워드를 포함하는 메시지

        userNotificationService.sendNotification(userId, message);

        ArgumentCaptor<UserNotification> captor = ArgumentCaptor.forClass(UserNotification.class);
        verify(userNotificationRepository).save(captor.capture());

        UserNotification savedNotification = captor.getValue();

        // 키워드가 메시지에 포함되어 있는 경우
        assertThat(savedNotification.getMessage()).contains("발견된 키워드: " + keyword);
    }

    @Test
    public void testSendNotification_KeywordNotFound() {
        String userId = "abc123";
        String message = "폭풍이다두두두두ㅜㄷ"; // 키워드를 포함하지 않는 메시지

        userNotificationService.sendNotification(userId, message);

        ArgumentCaptor<UserNotification> captor = ArgumentCaptor.forClass(UserNotification.class);
        verify(userNotificationRepository).save(captor.capture());

        UserNotification savedNotification = captor.getValue();

        // 키워드가 메시지에 포함되어 있지 않은 경우
        assertThat(savedNotification.getMessage()).isEqualTo("키워드에 해당하지 않습니다");
    }
}

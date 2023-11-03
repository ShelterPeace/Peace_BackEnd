package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.service.MsgService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledMsg {

    private final MsgService msgService;

    public ScheduledMsg(MsgService msgService) {
        this.msgService = msgService;
    }

    @Scheduled(fixedRate = 60000) // 1분 마다 업데이트
    public void updateData() {
        boolean success = msgService.extractDisasterMsgData();

        if (success) {
            System.out.println("데이터가 업데이트되었습니다.");
        } else {
            System.out.println("업데이트 할 내용이 없습니다.");
        }
    }
}

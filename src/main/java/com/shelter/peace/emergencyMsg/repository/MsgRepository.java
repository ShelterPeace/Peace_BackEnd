package com.shelter.peace.emergencyMsg.repository;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.service.dto.DisasterMsgDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MsgRepository extends JpaRepository<DisasterMsg, Long> {
    DisasterMsgDTO save(DisasterMsgDTO disasterMsgDTO);
    List<DisasterMsg> findAll();

    boolean existsByCreateDateAndLocationIdAndLocationNameAndMd101SnAndMessageAndSendPlatform(
            String createDate, String locationId, String locationName,
            int md101Sn, String message, String sendPlatform);
}

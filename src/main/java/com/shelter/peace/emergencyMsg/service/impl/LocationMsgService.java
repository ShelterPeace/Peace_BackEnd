package com.shelter.peace.emergencyMsg.service.impl;

import com.shelter.peace.emergencyMsg.entity.DisasterMsg;
import com.shelter.peace.emergencyMsg.entity.UserMsgLocation;
import com.shelter.peace.emergencyMsg.repository.MsgRepository;
import com.shelter.peace.emergencyMsg.repository.UserMsgLocationRepository;
import com.shelter.peace.user.entity.User;
import com.shelter.peace.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LocationMsgService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MsgRepository msgRepository;
    @Autowired
    private UserMsgLocationRepository userMsgLocationRepository;

    // 사용자의 지역 정보와 재난문자 데이터를 비교하여 일치하는 데이터를 선택하는 메서드
    public List<DisasterMsg> getDisasterMsgsForUser(Long userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return null;
        }

        List<UserMsgLocation> userLocations = user.getUserMsgLocations();
        List<DisasterMsg> matchedDisasterMsgs = new ArrayList<>();
        List<DisasterMsg> allDisasterMsgs = msgRepository.findAll();

        for (DisasterMsg disasterMsg : allDisasterMsgs) {
            for (UserMsgLocation userLocation : userLocations) {
                if (disasterMsg.getLocationName().contains(userLocation.getLocationName())) {
                    matchedDisasterMsgs.add(disasterMsg);
                }
            }
        }

        return matchedDisasterMsgs;
    }

    public String setUserLocation(Long userId, String locationName) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<UserMsgLocation> existingLocations = userMsgLocationRepository.findByUser(user);
        for (UserMsgLocation existingLocation : existingLocations) {
            if (existingLocation.getLocationName().contains(locationName)) {
                return "이미 설정된 지역입니다.";
            }
        }

        UserMsgLocation userMsgLocation = new UserMsgLocation();
        userMsgLocation.setUser(user);
        userMsgLocation.setLocationName(locationName);

        userMsgLocationRepository.save(userMsgLocation);

        return "지역설정이 완료되었습니다.";
    }

    public void deleteUserLocation(Long userId, String locationName) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<UserMsgLocation> userMsgLocations = userMsgLocationRepository.findByUser(user);
        UserMsgLocation targetLocation = null;
        for (UserMsgLocation userMsgLocation : userMsgLocations) {
            if (userMsgLocation.getLocationName().equals(locationName)) {
                targetLocation = userMsgLocation;
                break;
            }
        }

        if (targetLocation != null) {
            userMsgLocationRepository.delete(targetLocation);
        } else {
            throw new RuntimeException("지정된 지역이 사용자의 설정에 없습니다.");
        }
    }
}

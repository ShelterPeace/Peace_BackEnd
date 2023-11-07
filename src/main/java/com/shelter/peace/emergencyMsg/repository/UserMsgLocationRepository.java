package com.shelter.peace.emergencyMsg.repository;

import com.shelter.peace.emergencyMsg.entity.UserMsgLocation;
import com.shelter.peace.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMsgLocationRepository extends JpaRepository<UserMsgLocation, Long> {

    List<UserMsgLocation> findByUser(User user);
}

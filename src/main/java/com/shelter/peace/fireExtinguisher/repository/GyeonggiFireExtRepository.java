package com.shelter.peace.fireExtinguisher.repository;

import com.google.gson.JsonArray;
import com.shelter.peace.fireExtinguisher.entity.GyeonggiFireExt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GyeonggiFireExtRepository extends JpaRepository<GyeonggiFireExt,Long> {
    Optional<GyeonggiFireExt> findByRefineLotnoAddr(String refineLotnoAddr);
}

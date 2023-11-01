package com.shelter.peace.weather.repository;

import com.shelter.peace.weather.entity.InterestArea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AreaRepository extends JpaRepository<InterestArea, Long> {
    Optional<InterestArea> findByUserNo(long userNo);
}

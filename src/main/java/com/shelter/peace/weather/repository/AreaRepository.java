package com.shelter.peace.weather.repository;

import com.shelter.peace.weather.entity.InterestArea;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaRepository extends JpaRepository<InterestArea, Long> {
}

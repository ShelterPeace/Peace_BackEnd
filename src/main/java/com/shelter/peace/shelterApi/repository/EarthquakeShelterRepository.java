package com.shelter.peace.shelterApi.repository;

import com.shelter.peace.shelterApi.entity.EarthquakeShelter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EarthquakeShelterRepository extends JpaRepository<EarthquakeShelter, Long> {
    EarthquakeShelter findByArcd(String arcd);
}

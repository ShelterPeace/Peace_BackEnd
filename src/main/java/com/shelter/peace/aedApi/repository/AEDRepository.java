package com.shelter.peace.aedApi.repository;

import com.shelter.peace.aedApi.entity.AedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AEDRepository extends JpaRepository<AedEntity,Long> {
    AedEntity findByUniqueKey(String uniqueKey);
}

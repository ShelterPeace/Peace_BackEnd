package com.shelter.peace.fireExtinguisher.repository;

import com.shelter.peace.fireExtinguisher.entity.FireExtinguisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FireExtinguisherRepository extends JpaRepository<FireExtinguisher, Long> {
    Page<FireExtinguisher> findAll(Pageable pageable);
}

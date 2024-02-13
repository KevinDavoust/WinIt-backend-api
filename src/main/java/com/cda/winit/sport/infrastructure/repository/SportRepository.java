package com.cda.winit.sport.infrastructure.repository;

import com.cda.winit.sport.domain.entity.Sport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportRepository extends JpaRepository<Sport, Long> {
}

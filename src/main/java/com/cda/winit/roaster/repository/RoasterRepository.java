package com.cda.winit.roaster.repository;

import com.cda.winit.roaster.domain.entity.Roaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoasterRepository extends JpaRepository<Roaster, Long> {
}

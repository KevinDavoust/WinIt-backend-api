package com.cda.winit.tournament.infrastructure.repository;

import com.cda.winit.tournament.domain.entity.Tournament;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long>, JpaSpecificationExecutor<Tournament> {

    List<Tournament> findAll(Specification<Tournament> specification);
 }

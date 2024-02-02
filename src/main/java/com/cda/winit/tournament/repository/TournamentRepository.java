package com.cda.winit.tournament.repository;

import com.cda.winit.tournament.domain.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}

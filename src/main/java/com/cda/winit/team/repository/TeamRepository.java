package com.cda.winit.team.repository;

import com.cda.winit.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);

    List<Team> findByLeadTeamId(Long leadTeamId);
}

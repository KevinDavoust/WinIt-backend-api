package com.cda.winit.team.repository;

import com.cda.winit.team.domain.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    boolean existsByName(String name);

    Optional<Team> findTeamByName(String name);

    List<Team> findByLeaderTeamId(Long leaderTeamId);

    void deleteByName(String name);
}

package com.cda.winit.team.repository;

import com.cda.winit.team.domain.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    List<UserTeam> findAllByTeamId(Long teamId);
}

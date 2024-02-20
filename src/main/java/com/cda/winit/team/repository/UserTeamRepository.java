package com.cda.winit.team.repository;

import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.entity.UserTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
   /* @Query("SELECT ut.user.name FROM UserTeam ut WHERE ut.team.id = ?1")
    List<String> findUserNamesByTeamId(Long teamId); */
}

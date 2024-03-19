package com.cda.winit.team.repository;

import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.entity.UserTeam;
import com.cda.winit.user.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserTeamRepository extends JpaRepository<UserTeam, Long> {
    List<UserTeam> findAllByTeamId(Long teamId);

    boolean existsByUserAndTeam(User user, Team team);

    void deleteByUserAndTeam(User user, Team team);

    void deleteByTeamId(Long teamId);

    @Query("SELECT COUNT(ut) FROM UserTeam ut WHERE ut.team.id = :teamId")
    int countMembersByTeamId(Long teamId);
}

package com.cda.winit.team.domain.service.interfaces;


import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.repository.exception.TeamServiceException;

import java.util.List;

public interface ITeamService {

    List<TeamDto> getAllTeamsCreatedByLeader(String username);

    TeamDto getTeamByTeamName(String teamName);

    void createTeamWithLeader(String username, Long teamId) throws RuntimeException;

    void deleteTeam(String teamName) throws TeamServiceException;

    Team mapTeamDtoToEntity(TeamDto teamDto, String username);

    boolean verifyTeamLead(String teamName, String username);
}

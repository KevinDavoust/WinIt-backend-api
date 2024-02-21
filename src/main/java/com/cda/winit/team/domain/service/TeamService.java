package com.cda.winit.team.domain.service;

import com.cda.winit.team.domain.dto.MemberDto;
import com.cda.winit.team.domain.entity.UserTeam;
import com.cda.winit.team.repository.UserTeamRepository;
import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.mapper.TeamMapper;
import com.cda.winit.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;
    private final UserTeamService userTeamService;

    public Team mapTeamDTOToEntity(TeamDto teamDto, String username) {
        return teamMapper.toEntity(teamDto, username);
    }

    public List<TeamDto> listTeamsCreatedByUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        List<Team> teams = teamRepository.findByLeadTeamId(user.getId());
        List<TeamDto> teamDtos = new ArrayList<>();
        for (Team team : teams) {
            teamDtos.add(teamMapper.toDto(team));
        }
        return teamDtos;
    }

    public TeamDto getTeamByName(String teamName) {

        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

        return teamMapper.toDto(team);
    }
    public void saveTeam(Team team) {
        teamRepository.save(team);
    }

    public List<MemberDto> memberByTeam(String teamName) {
        Team team = teamRepository.findTeamByName(teamName)
                .orElseThrow(() -> new RuntimeException("Team not found with name: " + teamName));

        return userTeamService.getAllMemberByTeamId(team.getId());
    }
}

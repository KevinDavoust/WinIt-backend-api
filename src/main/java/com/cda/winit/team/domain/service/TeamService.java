package com.cda.winit.team.domain.service;

import com.cda.winit.auth.domain.entity.User;
import com.cda.winit.auth.infrastructure.repository.UserRepository;
import com.cda.winit.sport.domain.service.SportService;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.mapper.TeamMapper;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.exception.TeamNameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TeamMapper teamMapper;

    public Team mapTeamDTOToEntity(TeamDto teamDto, String username) {
        return teamMapper.toEntity(teamDto, username);
    }

    public List<Team> listTeamsCreatedByUser(String username) {
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        return teamRepository.findByLeadTeamId(user.getId());
    }

    public void saveTeam(Team team) {
        teamRepository.save(team);
    }
}

package com.cda.winit.team.domain.service.mapper;

import com.cda.winit.user.domain.entity.User;
import com.cda.winit.user.infrastructure.repository.UserRepository;
import com.cda.winit.sport.domain.service.SportService;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.exception.TeamNameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamMapper {

    private final SportService sportService;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;

    public Team toEntity(TeamDto teamDto, String username) {
        if (teamRepository.existsByName(teamDto.getName())) {
            throw new TeamNameAlreadyExistsException("Le nom de l'équipe est déjà pris");
        }
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + username));

        Team team = new Team();
        team.setName(teamDto.getName());
        Long sportId = sportService.findSportIdByName(teamDto.getSport());
        team.setSport_id(sportId);
        team.setLeaderTeamId(user.getId());
        return team;
    }

    public TeamDto toDto(Team team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setName(team.getName());
        teamDto.setSport(sportService.findSportNameById(team.getSport_id()));
        teamDto.setTotalPlayers(sportService.findSportNumberPlayer(team.getSport_id()));
        Boolean isValidated = team.getIsValidated();
        if(isValidated != null && isValidated){
            teamDto.setValidated(isValidated);
        }
        return teamDto;
    }
}

package com.cda.winit.team.domain.service;

import com.cda.winit.sport.domain.service.SportService;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final SportService sportService;
    private final TeamRepository teamRepository;
    public Team mapRoasterDTOToEntity(TeamDto teamDto) {
        Team team = new Team();
        team.setName(teamDto.getName());
        Long sportId = sportService.findSportIdByName(teamDto.getSport());
        team.setSport_id(sportId);
        return team;
    }

    public void saveRoaster(Team team) {
        teamRepository.save(team);
    }
}

package com.cda.winit.team.application;

import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;

    @GetMapping("/:id")
    public void roastersByUser( ) {

    }
    @PostMapping("/add")
    public void createRoaster(@RequestBody TeamDto teamDto) {
        Team team = teamService.mapRoasterDTOToEntity(teamDto);
        teamService.saveRoaster(team);
    }
}

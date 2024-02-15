package com.cda.winit.team.application;

import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.TeamService;
import com.cda.winit.team.domain.service.UserTeamService;
import com.cda.winit.team.repository.exception.ListTeamByUserAlreadyExistsException;
import com.cda.winit.team.repository.exception.TeamNameAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/teams")
@RequiredArgsConstructor
public class TeamController {

    private final TeamService teamService;
    private final UserTeamService userTeamService;

    @GetMapping
    public ResponseEntity<?> listTeamsCreatedByUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            List<TeamDto> teams = teamService.listTeamsCreatedByUser(username);
            return ResponseEntity.ok().body(teams);
        } catch (ListTeamByUserAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body("L'utilisateur le possède pas d'équipe.");
        }
    }
    @PostMapping
    public ResponseEntity<Object> createTeam(@RequestBody TeamDto teamDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Team team = teamService.mapTeamDTOToEntity(teamDto, username);
            teamService.saveTeam(team);

            userTeamService.createUserTeam(username, team.getId());

            return ResponseEntity.ok().body(Collections.singletonMap("message", "L'équipe a bien été enregistrée"));
        } catch (TeamNameAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body("Le nom de l'équipe est déjà pris");
        }
    }
}

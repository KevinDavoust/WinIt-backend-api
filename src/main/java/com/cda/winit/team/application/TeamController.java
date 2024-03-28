package com.cda.winit.team.application;

import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.TeamService;
import com.cda.winit.team.repository.TeamRepository;
import com.cda.winit.team.repository.exception.ListTeamByUserAlreadyExistsException;
import com.cda.winit.team.repository.exception.TeamNameAlreadyExistsException;
import com.cda.winit.team.repository.exception.TeamServiceException;
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
    private final TeamRepository teamRepository;

    @GetMapping
    public ResponseEntity<?> getAllTeamsOfUser() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            List<TeamDto> teams = teamService.getAllTeamsCreatedByLeader(username);
            return ResponseEntity.ok().body(teams);
        } catch (ListTeamByUserAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body("L'utilisateur le possède pas d'équipe.");
        }
    }

    @GetMapping("/{teamName}")
    public ResponseEntity<?> getTeamOfUser(@PathVariable String teamName) {
        try {
            TeamDto team = teamService.getTeamByTeamName(teamName);

            if (team != null) {
                return ResponseEntity.ok().body(team);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de la récupération de l'équipe.");
        }
    }

    @PostMapping
    public ResponseEntity<Object> createTeam(@RequestBody TeamDto teamDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            Team team = teamService.mapTeamDtoToEntity(teamDto, username);
            teamRepository.save(team);

            teamService.createTeamWithLeader(username, team.getId());

            return ResponseEntity.ok().body(Collections.singletonMap("message", "L'équipe a bien été enregistrée"));
        } catch (TeamNameAlreadyExistsException ex) {
            return ResponseEntity.badRequest().body("Le nom de l'équipe est déjà pris");
        }
    }

    @DeleteMapping("/{teamName}")
    public ResponseEntity<?> deleteTeam(@PathVariable String teamName) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                teamService.deleteTeam(teamName);
                return ResponseEntity.ok().body(Collections.singletonMap("message", "L'équipe a bien été supprimé"));
            } else {
                return ResponseEntity.badRequest().body("Vous n'êtes pas autorisé à supprimer l'équipe.");
            }
        } catch (TeamServiceException ex) {
            return ResponseEntity.badRequest().body("Erreur : " + ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de la suppression de l'équipe : " + ex.getMessage());
        }
    }
}

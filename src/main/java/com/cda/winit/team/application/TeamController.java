package com.cda.winit.team.application;

import com.cda.winit.team.domain.dto.MemberDto;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.domain.service.TeamService;
import com.cda.winit.team.domain.service.UserTeamService;
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

    @GetMapping("/{teamName}")
    public ResponseEntity<?> teamByTeamName(@PathVariable String teamName) {
        try {
            TeamDto team = teamService.getTeamByName(teamName);

            if (team != null) {
                return ResponseEntity.ok().body(team);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de la récupération de l'équipe.");
        }
    }

    @GetMapping("/{teamName}/members")
    public ResponseEntity<?> memberByTeam(@PathVariable String teamName) {
        try {
            List<MemberDto> members = teamService.memberByTeam(teamName);
            return ResponseEntity.ok(members);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de la récupération des membres de l'équipe.");
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

    @PostMapping("/{teamName}/members")
    public ResponseEntity<Object> createMember(@PathVariable String teamName, @RequestBody MemberDto memberDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                userTeamService.createMember(teamName, memberDto);
                return ResponseEntity.ok().body(Collections.singletonMap("message", "Le membre a bien été enregistré dans l'équipe"));
            } else {
                return ResponseEntity.badRequest().body("Vous n'êtes pas autorisé à ajouter un membre à cette équipe.");
            }
        } catch (TeamServiceException ex) {
            return ResponseEntity.badRequest().body("Erreur : " + ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de l'inscription du membre dans l'équipe : " + ex.getMessage());
        }
    }

    @DeleteMapping("/{teamName}/members/{memberName}")
    public ResponseEntity<?> deletedMember(@PathVariable String teamName, @PathVariable String memberName) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                userTeamService.deleteMember(teamName, memberName);
                return ResponseEntity.ok().body(Collections.singletonMap("message", "Le membre a bien été supprimé de l'équipe"));
            } else {
                return ResponseEntity.badRequest().body("Vous n'êtes pas autorisé à supprimer un membre de équipe.");
            }
        } catch (TeamServiceException ex) {
            return ResponseEntity.badRequest().body("Erreur : " + ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de la suppression du membre de l'équipe : " + ex.getMessage());
        }
    }
}

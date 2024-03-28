package com.cda.winit.member.application;

import com.cda.winit.member.domain.dto.MemberRequest;
import com.cda.winit.member.domain.service.MemberService;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.service.TeamService;
import com.cda.winit.team.repository.exception.TeamServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberController {

    private final TeamService teamService;
    private final MemberService memberService;

    @PostMapping("/{teamName}")
    public ResponseEntity<?> addMemberToTeam(@PathVariable String teamName, @RequestBody MemberRequest memberRequest) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                memberService.addMemberToTeam(teamName, memberRequest);

                TeamDto updatedTeam = teamService.getTeamByTeamName(teamName);

                return ResponseEntity.ok().body(updatedTeam);
            } else {
                return ResponseEntity.badRequest().body("Vous n'êtes pas autorisé à ajouter un membre à cette équipe.");
            }
        } catch (TeamServiceException ex) {
            return ResponseEntity.badRequest().body("Erreur : " + ex.getMessage());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de l'inscription du membre dans l'équipe : " + ex.getMessage());
        }
    }

    @DeleteMapping("/{teamName}/{memberId}")
    public ResponseEntity<?> deleteMemberFromTeam(@PathVariable String teamName, @PathVariable String memberId) {
        System.out.println(teamName);
        System.out.println(memberId);

        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                memberService.deleteMember(teamName, Long.valueOf(memberId));

                TeamDto updatedTeam = teamService.getTeamByTeamName(teamName);

                return ResponseEntity.ok().body(updatedTeam);
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

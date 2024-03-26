package com.cda.winit.member.application;

import com.cda.winit.member.domain.service.MemberService;
import com.cda.winit.member.domain.dto.MemberRequestDto;
import com.cda.winit.team.domain.service.TeamService;
import com.cda.winit.team.repository.exception.TeamServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;

public class MemberController {

    private TeamService teamService;
    private MemberService memberService;

    @PostMapping("/{teamName}/members")
    public ResponseEntity<Object> addMember(@PathVariable String teamName, @RequestBody MemberRequestDto memberRequestDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                memberService.addMemberToTeam(teamName, memberRequestDto);
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

    @DeleteMapping("/{teamName}/members")
    public ResponseEntity<?> deletedMember(@PathVariable String teamName, @RequestBody MemberRequestDto memberDto) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();

            boolean isTeamLead = teamService.verifyTeamLead(teamName, username);

            if (isTeamLead) {
                memberService.deleteMember(teamName, memberDto.getEmail());
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

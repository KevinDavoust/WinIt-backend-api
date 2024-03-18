package com.cda.winit.tournament.application;

import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.domain.service.TournamentService;
import com.cda.winit.tournament.infrastructure.repository.TournamentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentRepository tournamentRepository;
    

    @PostMapping(value = "/create")
    public ResponseEntity<Long> create(
            @ModelAttribute TournamentCreationDto tournamentCreationDto) throws Exception {

            Long tournamentId = tournamentService.createTournament(tournamentCreationDto);

            return ResponseEntity.ok().body(tournamentId);
    }

    @GetMapping("/")
    public ResponseEntity<List<Tournament>> getAll(HttpServletRequest request) {
        try {
            List<Tournament> tournaments = tournamentRepository.findAll();
            return ResponseEntity.ok(tournaments);
        } catch (Exception e) {
            String errorMessage = "An error occurred while processing the request.";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    @GetMapping("/{id}")
    public Tournament getById(@PathVariable Long id, HttpServletRequest request) throws AccessDeniedException {
        String role = SecurityContextHolder.getContext().getAuthentication().getAuthorities().toString();

        if (role.equals("[ROLE_ADMIN]") || role.equals("[ROLE_USER]")) {
            Tournament  tournament = tournamentService.getOneTournament(id);
            return tournament;

        } else {
            request.setAttribute("access_denied", "You do not have sufficient rights to access to this resource");
            throw new AccessDeniedException("User does not have the correct rights to access to this resource");
        }
    }
}


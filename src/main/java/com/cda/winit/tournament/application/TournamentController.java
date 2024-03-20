package com.cda.winit.tournament.application;

import com.cda.winit.tournament.domain.dto.TournamentCarouselDTO;
import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.domain.mappers.TournamentEntityMappers;
import com.cda.winit.tournament.domain.service.TournamentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
@RequiredArgsConstructor
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentEntityMappers mapper;

    @GetMapping("/")
    public ArrayList<TournamentCarouselDTO> getAll() {
        List<Tournament> tournaments = tournamentService.getAllTournaments();
        var tournamentsDtos = new ArrayList<TournamentCarouselDTO>();
        tournaments.forEach(tournament -> tournamentsDtos.add(mapper.entityToCarouselDTO(tournament)));
        return tournamentsDtos;
    }


    @PostMapping(value = "/create")
    public ResponseEntity<Long> create(
            @ModelAttribute TournamentCreationDto tournamentCreationDto) throws Exception {

            Long tournamentId = tournamentService.createTournament(tournamentCreationDto);

            return ResponseEntity.ok().body(tournamentId);
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


package com.cda.winit.tournament.application;

import com.cda.winit.tournament.domain.dto.TournamentCarouselDTO;
import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.dto.TournamentDetailsDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.domain.service.mappers.TournamentEntityMappers;
import com.cda.winit.tournament.domain.service.TournamentService;
import com.cda.winit.tournament.infrastructure.repository.exception.TournamentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            TournamentDetailsDto tournamentDto = tournamentService.getOneTournament(id);
            return ResponseEntity.ok().body(tournamentDto);
        } catch (TournamentNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Une erreur s'est produite lors de la récupération du tournois.");
        }
    }
}


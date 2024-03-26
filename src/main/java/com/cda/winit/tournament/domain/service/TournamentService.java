package com.cda.winit.tournament.domain.service;

import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.dto.TournamentDetailsDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.domain.service.mappers.TournamentEntityMappers;
import com.cda.winit.tournament.domain.service.interfaces.ITournamentService;
import com.cda.winit.tournament.infrastructure.repository.TournamentRepository;
import com.cda.winit.tournament.infrastructure.repository.exception.TournamentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService implements ITournamentService {

    private final TournamentRepository repository;
    private final TournamentEntityMappers tournamentEntityMappers;

    public Long createTournament(TournamentCreationDto newTournamentDto) throws Exception {
        Tournament tournamentCreated = repository.save(tournamentEntityMappers.ToCreationEntity(newTournamentDto));
        
        return tournamentCreated.getId();
    }

    public List<Tournament> getAllTournaments() {
        return repository.findAll();
    }

    public TournamentDetailsDto  getOneTournament(Long id) {
        Tournament tournament = repository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
        TournamentDetailsDto tournamentDetailsDto = tournamentEntityMappers.entityToTournamentDetailsDTO(tournament);
        return tournamentDetailsDto;
    }
}

package com.cda.winit.tournament.domain.service;

import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.domain.mappers.TournamentEntityMappers;
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

    public Tournament createTournament(TournamentCreationDto newTournamentDto) throws Exception {
        Tournament newTournament = tournamentEntityMappers.ToEntity(newTournamentDto);
        return repository.save(newTournament);
    }

    public List<Tournament> getAllTournament() {
        return repository.findAll();
    }

    public Tournament getOneTournament(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
    }
}

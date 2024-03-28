package com.cda.winit.tournament.domain.service.interfaces;

import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.dto.TournamentDetailsDto;
import com.cda.winit.tournament.domain.entity.Tournament;

import java.util.List;

public interface ITournamentService {

    Long createTournament(TournamentCreationDto newTournamentDto) throws Exception;
    public List<Tournament> getAllTournaments();
    public TournamentDetailsDto getOneTournament(Long id);
}

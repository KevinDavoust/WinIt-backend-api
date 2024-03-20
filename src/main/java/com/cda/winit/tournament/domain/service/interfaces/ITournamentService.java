package com.cda.winit.tournament.domain.service.interfaces;

import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;

import java.util.List;

public interface ITournamentService {
    public Tournament createTournament(TournamentCreationDto tournament) throws Exception;
    public List<Tournament> getAllTournaments();
    public Tournament getOneTournament(Long id);
}

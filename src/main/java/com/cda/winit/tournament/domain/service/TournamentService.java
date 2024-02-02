package com.cda.winit.tournament.domain.service;

import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.repository.TournamentRepository;
import com.cda.winit.tournament.repository.exception.TournamentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository repository;

    public List<Tournament> getAllTournament() {
        return repository.findAll();
    }
    public Tournament getOneTournament(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
    }
}

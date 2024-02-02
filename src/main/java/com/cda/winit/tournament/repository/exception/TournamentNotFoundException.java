package com.cda.winit.tournament.repository.exception;

public class TournamentNotFoundException extends RuntimeException {
    public TournamentNotFoundException(Long id) {
        super("Could not find tournament with id " + id);
    }
}

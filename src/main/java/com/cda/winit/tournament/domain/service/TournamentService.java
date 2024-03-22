package com.cda.winit.tournament.domain.service;

import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import com.cda.winit.tournament.domain.mappers.TournamentEntityMappers;
import com.cda.winit.tournament.domain.service.interfaces.ITournamentService;
import com.cda.winit.tournament.infrastructure.repository.TournamentRepository;
import com.cda.winit.tournament.infrastructure.repository.TournamentSpecification;
import com.cda.winit.tournament.infrastructure.repository.exception.TournamentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

    public Tournament getOneTournament(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new TournamentNotFoundException(id));
    }

    public List<Tournament> getTournamentsByFilter(
            String searchValue,
            String selectedSport,
            Boolean chronologicalFilter,
            Boolean showOnlyUpcomingTournaments,
            Boolean showNonFullTournaments
    ){
        final Specification<Tournament> specification =
                TournamentSpecification.filterTournament(
                        searchValue,
                        selectedSport,
                        showOnlyUpcomingTournaments,
                        showNonFullTournaments
                );
        Sort sort = this.sortByNameOrDate(chronologicalFilter);

        return repository.findAll(specification, sort);
    }

    public Sort sortByNameOrDate(Boolean chronologicalFilter) {
        if (chronologicalFilter) {
            return Sort.by(Sort.Direction.ASC, "date");
        } else {
            return Sort.by(Sort.Direction.ASC, "name");
        }
    }
}

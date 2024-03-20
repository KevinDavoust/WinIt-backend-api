package com.cda.winit.tournament.domain.mappers;

import com.cda.winit.shared.ImageUploadService;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.infrastructure.repository.SportRepository;
import com.cda.winit.tournament.domain.dto.TournamentCarouselDTO;
import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Optional;

@Service
public class TournamentEntityMappers {
    private final SportRepository sportRepository;
    private ImageUploadService imageUploadService;

    public TournamentEntityMappers(
            SportRepository sportRepository,
            ImageUploadService imageUploadService) {
        this.sportRepository = sportRepository;
        this.imageUploadService = imageUploadService;
    }

    public Tournament ToEntity(TournamentCreationDto model) throws Exception {
        Tournament entity = new Tournament();

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        entity.setName(model.getName());
        entity.setDate(df.parse(model.getDate()));
        entity.setPlace(model.getPlace());
        entity.setPlayersPerTeam(model.getPlayersPerTeam());
        entity.setMaxNumberOfTeams(model.getMaxTeams());
        entity.setGameLength(model.getGameLength());
        entity.setPlayerCategory(model.getPlayerCategory());
        entity.setPrivacy(model.getPrivacy());
        entity.setFormat(model.getTournamentFormat());
        entity.setCreatedAt(LocalDateTime.now());

        Optional<Sport> sport = sportRepository.findByName(model.getSportName());
        entity.setSport(sport.orElseThrow());

        String imageUrl = !model.getTournamentBanner().isEmpty() ?
                imageUploadService.generateImageUrlAndSaveImage(model.getTournamentBanner()) :
                null;
        entity.setImageUrl(imageUrl);

        entity.setInscriptionLimitDate(df.parse(
                (model.getInscriptionLimitDate() != null) ?
                        model.getInscriptionLimitDate() :
                        model.getDate()));

        entity.setMinNumberOfTeams(model.getMinTeams());
        entity.setCurrentNumberOfParticipants(null);
        entity.setMatches(null);
        entity.setUpdatedAt(null);

        return entity;
    }

    public TournamentCarouselDTO entityToCarouselDTO(Tournament model) {
        TournamentCarouselDTO dto = new TournamentCarouselDTO();
        dto.setId(model.getId());
        dto.setName(model.getName());
        dto.setDate(model.getDate());
        dto.setPlace(model.getPlace());
        dto.setImageUrl(model.getImageUrl());
        dto.setCurrentNumberOfParticipants(model.getCurrentNumberOfParticipants());
        dto.setMaxNumberOfTeams(model.getMaxNumberOfTeams());
        dto.setSport(model.getSport().getName());
        return dto;
    }
}

package com.cda.winit.tournament.domain.mappers;

import com.cda.winit.shared.ImageUploadService;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.infrastructure.repository.SportRepository;
import com.cda.winit.tournament.domain.dto.TournamentCreationDto;
import com.cda.winit.tournament.domain.entity.Tournament;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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

    public Tournament ToCreationEntity(TournamentCreationDto model) throws Exception {
        Tournament entity = new Tournament();

        this.setFormProperties(entity, model);
        this.setNonFormProperties(entity);

        return entity;
    }

    private void setFormProperties(Tournament entity, TournamentCreationDto model) throws ParseException, IOException {
        this.setNonNullableProperties(entity, model);
        this.setNullableProperties(entity, model);
    }

    private void setNonNullableProperties(Tournament entity, TournamentCreationDto model) throws ParseException {
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
    }

    private void setNullableProperties(Tournament entity, TournamentCreationDto model) throws IOException, ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        Optional<Sport> sport = sportRepository.findByName(model.getSportName());
        entity.setSport(sport.orElseThrow());

        String imageUrl = model.getTournamentBanner() != null && !model.getTournamentBanner().isEmpty() ?
                imageUploadService.generateImageUrlAndSaveImage(model.getTournamentBanner()) :
                null;
        entity.setImageUrl(imageUrl);

        entity.setInscriptionLimitDate(df.parse(
                (model.getInscriptionLimitDate() != null) ?
                        model.getInscriptionLimitDate() :
                        model.getDate()));

        entity.setMinNumberOfTeams(model.getMinTeams());
    }

    private void setNonFormProperties(Tournament entity) {
        entity.setCurrentNumberOfParticipants(null);
        entity.setMatches(null);
        entity.setUpdatedAt(null);
    }
}

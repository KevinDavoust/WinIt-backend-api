package com.cda.winit.tournament.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TournamentCreationDto {

    @NonNull
    @JsonProperty("name")
    private String name;

    @NonNull
    @JsonProperty("place")
    private String place;

    @NonNull
    @JsonProperty("date")
    private String date;

    @NonNull
    @JsonProperty("sportName")
    private String sportName;

    @NonNull
    @JsonProperty("playersPerTeam")
    private int playersPerTeam;

    @NonNull
    @JsonProperty("maxTeams")
    private int maxTeams;

    @NonNull
    @JsonProperty("gameLength")
    private int gameLength;

    @NonNull
    @JsonProperty("privacy")
    private String privacy;

    @NonNull
    @JsonProperty("playerCategory")
    private String playerCategory;

    @NonNull
    @JsonProperty("tournamentFormat")
    private String tournamentFormat;

    @Nullable
    @JsonProperty("inscriptionLimitDate")
    private String inscriptionLimitDate;

    @Nullable
    @JsonProperty("minTeams")
    private int minTeams;

    @Nullable
    @JsonProperty("tournamentBanner")
    private MultipartFile tournamentBanner;
}

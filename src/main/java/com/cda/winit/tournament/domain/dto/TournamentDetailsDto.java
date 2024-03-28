package com.cda.winit.tournament.domain.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TournamentDetailsDto {
    private Long id;
    private String name;
    private Date date;
    private String place;
    private Long currentNumberOfParticipants;
    private int maxNumberOfTeams;
    private String imageUrl;
    private String sport;
    private Date inscriptionLimitDate;
    private String format;
    private String privacy;
}

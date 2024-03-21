package com.cda.winit.tournament.domain.dto;


import lombok.*;

import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TournamentCarouselDTO {

    private Long id;
    private String name;
    private Date date;
    private String place;
    private Long currentNumberOfParticipants;
    private int maxNumberOfTeams;
    private String imageUrl;
    private String sport;

}

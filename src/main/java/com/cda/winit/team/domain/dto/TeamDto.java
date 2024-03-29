package com.cda.winit.team.domain.dto;

import lombok.Data;

@Data
public class TeamDto {
    private String name;
    private String sport;
    private String leadTeamName;
    private int numberOfPlayers;
    private int numberOfMemberInTeam;
}

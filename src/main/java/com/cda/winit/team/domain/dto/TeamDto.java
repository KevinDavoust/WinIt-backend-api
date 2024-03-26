package com.cda.winit.team.domain.dto;

import com.cda.winit.member.domain.dto.MemberResponse;
import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private String name;
    private String sport;
    private String leaderName;
    private int totalPlayers;
    private int teamMembersCount;
    private List<MemberResponse> members;
}

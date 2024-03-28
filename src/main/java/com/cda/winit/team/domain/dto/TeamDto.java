package com.cda.winit.team.domain.dto;

import com.cda.winit.member.domain.dto.MemberResponse;
import com.cda.winit.member.domain.entity.Member;
import lombok.Data;

import java.util.List;

@Data
public class TeamDto {
    private String name;
    private String sport;
    private String leaderName;
    private int totalPlayers;
    private int teamMembersCount;
    private boolean isValidated;
    private List<MemberResponse> members;
}

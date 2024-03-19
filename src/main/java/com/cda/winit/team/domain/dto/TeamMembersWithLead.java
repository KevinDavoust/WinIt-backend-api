package com.cda.winit.team.domain.dto;

import lombok.Data;

import java.util.List;

@Data
public class TeamMembersWithLead {
    private List<MemberDto> members;
    private String leadTeamName;
}

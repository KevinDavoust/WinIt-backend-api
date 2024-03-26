package com.cda.winit.team.domain.dto;

import com.cda.winit.member.domain.dto.MemberResponseDto;
import lombok.Data;

import java.util.List;

@Data
public class TeamMembersWithLead {
    private List<MemberResponseDto> members;
    private String leadTeamName;
}

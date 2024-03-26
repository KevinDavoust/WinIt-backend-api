package com.cda.winit.member.domain.service.interfaces;

import com.cda.winit.member.domain.dto.MemberRequestDto;
import com.cda.winit.member.infrastructure.exception.MemberServiceException;
import com.cda.winit.team.domain.dto.TeamMembersWithLead;

public interface IMemberService {

    TeamMembersWithLead getAllMemberByTeamId(Long teamId);

    void addMemberToTeam(String teamName, MemberRequestDto memberRequestDto) throws MemberServiceException;

    void deleteMember(String teamName, String memberName) throws MemberServiceException;
}

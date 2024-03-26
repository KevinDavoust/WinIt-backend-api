package com.cda.winit.member.domain.service.interfaces;

import com.cda.winit.member.domain.dto.MemberRequest;
import com.cda.winit.member.infrastructure.exception.MemberServiceException;

public interface IMemberService {

    void addMemberToTeam(String teamName, MemberRequest memberRequestDto) throws MemberServiceException;

    void deleteMember(String teamName, Long memberId) throws MemberServiceException;
}

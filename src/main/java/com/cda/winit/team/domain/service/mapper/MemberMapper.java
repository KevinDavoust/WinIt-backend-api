package com.cda.winit.team.domain.service.mapper;

import com.cda.winit.team.domain.dto.MemberDto;
import com.cda.winit.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMapper {
    public MemberDto toDto(User user) {
        MemberDto memberDto = new MemberDto();
        memberDto.setName(user.getFirstName());
        return memberDto;
    }
}

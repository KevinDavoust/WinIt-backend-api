package com.cda.winit.member.domain.service.mapper;

import com.cda.winit.member.domain.dto.MemberResponseDto;
import com.cda.winit.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMapper {
    public MemberResponseDto toDto(User user) {
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setName(user.getFirstName());
        return memberResponseDto;
    }
}

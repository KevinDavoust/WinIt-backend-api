package com.cda.winit.member.domain.service.mapper;

import com.cda.winit.member.domain.dto.MemberResponse;
import com.cda.winit.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMapper {
    public MemberResponse toDto(User user) {
        MemberResponse memberResponseDto = new MemberResponse();
        memberResponseDto.setId(user.getId());
        memberResponseDto.setFirstName(user.getFirstName());
        memberResponseDto.setLastName(user.getLastName());
        return memberResponseDto;
    }
}

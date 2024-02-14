package com.cda.winit.sport.domain.service.mapper;

import com.cda.winit.sport.domain.dto.SportDto;
import com.cda.winit.sport.domain.entity.Sport;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SportMapper {
    public List<SportDto> convertToDtoList(List<Sport> sports) {
        List<SportDto> sportDtoList = new ArrayList<>();

        for (Sport sport : sports) {
            SportDto sportDto = new SportDto();
            sportDto.setName(sport.getName());
            sportDtoList.add(sportDto);
        }

        return sportDtoList;
    }
}

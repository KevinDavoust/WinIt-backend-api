package com.cda.winit.sport.domain.service.mapper;

import com.cda.winit.sport.domain.dto.SportDto;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.infrastructure.repository.SportRepository;
import com.cda.winit.team.domain.dto.TeamDto;
import com.cda.winit.team.domain.entity.Team;
import com.cda.winit.team.repository.exception.TeamNameAlreadyExistsException;
import com.cda.winit.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;



@Service
@RequiredArgsConstructor
public class SportMapper {

    private final SportRepository sportRepository;

    public Sport toEntity(SportDto sportDto, String imageUrl) {

        Sport sport = new Sport();
        sport.setName(sportDto.getName());
        sport.setNumberOfPlayers(sportDto.getNumberOfPlayers());
        sport.setImageUrl(imageUrl);
        return sport;
    }
    public List<SportDto> convertToDtoList(List<Sport> sports) {
        List<SportDto> sportDtoList = new ArrayList<>();

        for (Sport sport : sports) {
            SportDto sportDto = new SportDto("", 0,"");
            sportDto.setName(sport.getName());
            sportDto.setImageUrl(sport.getImageUrl());
            sportDto.setNumberOfPlayers(sport.getNumberOfPlayers());
            sportDtoList.add(sportDto);
        }

        return sportDtoList;
    }
}

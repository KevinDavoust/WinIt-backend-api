package com.cda.winit.sport.domain.service;

import com.cda.winit.sport.domain.dto.SportDto;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.domain.service.mapper.SportMapper;
import com.cda.winit.sport.infrastructure.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;
    private final SportMapper sportMapper;
    public List<SportDto> findAllSport() {
        List<Sport> sports = sportRepository.findAll();
        List<SportDto> sportDtos = sportMapper.convertToDtoList(sports);
        return sportDtos;
    }

    public Long findSportIdByName(String sportName) {
        Optional<Sport> optionalSport = sportRepository.findByName(sportName);
        return optionalSport.map(Sport::getId).orElse(null);
    }

    public void saveSport(Sport sport) {
        sportRepository.save(sport);
    }

}

package com.cda.winit.sport.domain.service;

import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.infrastructure.repository.SportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SportService {

    private final SportRepository sportRepository;

    public List<Sport> findAllSport() {
        List<Sport> sports = sportRepository.findAll();
        System.out.println(sports);
        return sports;
    }
}

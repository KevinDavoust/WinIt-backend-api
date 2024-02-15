package com.cda.winit.sport.application;

import com.cda.winit.sport.domain.dto.SportDto;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.domain.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;
    @GetMapping("/")
    public List<SportDto> listSport() {
        return sportService.findAllSport();
    }
}

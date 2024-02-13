package com.cda.winit.roaster.domain.service;

import com.cda.winit.roaster.domain.dto.RoasterDto;
import com.cda.winit.roaster.domain.entity.Roaster;
import com.cda.winit.roaster.repository.RoasterRepository;
import com.cda.winit.sport.domain.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoasterService {

    private final SportService sportService;
    private final RoasterRepository roasterRepository;
    public Roaster mapRoasterDTOToEntity(RoasterDto roasterDTO) {
        Roaster roaster = new Roaster();
        roaster.setName(roasterDTO.getName());
        Long sportId = sportService.findSportIdByName(roasterDTO.getSport());
        roaster.setSport_id(sportId);
        return roaster;
    }

    public void saveRoaster(Roaster roaster) {
        roasterRepository.save(roaster);
    }
}

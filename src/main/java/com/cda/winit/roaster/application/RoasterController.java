package com.cda.winit.roaster.application;

import com.cda.winit.roaster.domain.dto.RoasterDto;
import com.cda.winit.roaster.domain.entity.Roaster;
import com.cda.winit.roaster.domain.service.RoasterService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/roasters")
@RequiredArgsConstructor
public class RoasterController {
    private RoasterService roasterService;

    @PostMapping("/add")
    public void createRoaster(@RequestBody RoasterDto roasterDTO) {
        Roaster roaster = roasterService.mapRoasterDTOToEntity(roasterDTO);
        roasterService.saveRoaster(roaster);
    }
}

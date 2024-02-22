package com.cda.winit.sport.application;

import com.cda.winit.shared.ImageUploadService;
import com.cda.winit.sport.domain.dto.SportDto;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.domain.service.SportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;
    private final ImageUploadService imageUploadService;

    @GetMapping("/")
    public List<SportDto> listSport() {
        return sportService.findAllSport();
    }

    @PostMapping( "/new")
    public ResponseEntity<Object> newSport(@RequestParam("name") String name, @RequestParam("numberOfPlayers") int numberOfPlayers, @RequestParam("newFile") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("Please load a file");
        }
        try {
            String imageUrl = imageUploadService.generateImageUrlAndSaveImage(file);

            Sport sport = new Sport();
            sport.setImageUrl(imageUrl);
            sport.setName(name);
            sport.setNumberOfPlayers(numberOfPlayers);
            sportService.saveSport(sport);

            return ResponseEntity.ok().body(Collections.singletonMap("message", "Le sport a bien été enregistré"));
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

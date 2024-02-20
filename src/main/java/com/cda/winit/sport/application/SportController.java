package com.cda.winit.sport.application;

import com.cda.winit.shared.ImageUploadService;
import com.cda.winit.sport.domain.dto.SportDto;
import com.cda.winit.sport.domain.entity.Sport;
import com.cda.winit.sport.domain.service.SportService;
import jakarta.servlet.ServletContext;
import lombok.*;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
            String UUID = imageUploadService.generateUUID();
            Path path = imageUploadService.getPath(UUID, file);
            String cleanFileName = imageUploadService.cleanOriginalFileName(Objects.requireNonNull(file.getOriginalFilename()));

            Sport sport = new Sport();
            sport.setImageUrl(UUID + cleanFileName);
            sport.setName(name);
            sport.setNumberOfPlayers(numberOfPlayers);
            sportService.saveSport(sport);

            imageUploadService.saveImage(path, file);

            return ResponseEntity.ok().body(Collections.singletonMap("message", "Le sport a bien été enregistré"));
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

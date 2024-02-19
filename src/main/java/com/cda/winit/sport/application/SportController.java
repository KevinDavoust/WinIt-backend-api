package com.cda.winit.sport.application;

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

@RestController
@RequestMapping("/api/sports")
@RequiredArgsConstructor
public class SportController {

    private final SportService sportService;
    private final Environment env;

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
            Path path = Paths.get(env.getProperty("spring.servlet.multipart.location") + file.getOriginalFilename());

            Sport sport = new Sport();
            sport.setImageUrl(String.valueOf(path));
            sport.setName(name);
            sport.setNumberOfPlayers(numberOfPlayers);
            sportService.saveSport(sport);

            byte[] bytes = file.getBytes();

            Files.write(path, bytes);

            return ResponseEntity.ok().body(Collections.singletonMap("message", "Le sport a bien été enregistré"));
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
}

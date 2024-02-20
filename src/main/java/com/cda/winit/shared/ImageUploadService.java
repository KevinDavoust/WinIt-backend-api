package com.cda.winit.shared;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
public class ImageUploadService {
    private final Environment env;

    public ImageUploadService(Environment env) {
        this.env = env;
    }

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public Path getPath(String UUID, MultipartFile file) {
       return Paths.get(env.getProperty("spring.servlet.multipart.location") + UUID + cleanOriginalFileName(Objects.requireNonNull(file.getOriginalFilename())));
    }

    public void saveImage(Path path, MultipartFile file) throws IOException {
        byte[] bytes = file.getBytes();
        Files.write(path, bytes);
    }

    public String cleanOriginalFileName(String originalFileName) {
        return originalFileName.replaceAll("[^a-zA-Z0-9.]", "-");
    }

}

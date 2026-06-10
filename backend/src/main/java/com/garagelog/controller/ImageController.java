package com.garagelog.controller;

import com.garagelog.service.ImageStorageService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/garagelog/imagenes")
public class ImageController {

    private static final Resource PLACEHOLDER = new ClassPathResource("static/placeholder.png");

    private final ImageStorageService storageService;

    public ImageController(ImageStorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/{filename:.+}")
    public ResponseEntity<byte[]> getImage(@PathVariable String filename) {
        try {
            Resource resource = storageService.load(filename);
            String lower = filename.toLowerCase();
            MediaType contentType;
            if (lower.endsWith(".png")) {
                contentType = MediaType.IMAGE_PNG;
            } else if (lower.endsWith(".jpg") || lower.endsWith(".jpeg")) {
                contentType = MediaType.IMAGE_JPEG;
            } else if (lower.endsWith(".gif")) {
                contentType = MediaType.IMAGE_GIF;
            } else if (lower.endsWith(".webp")) {
                contentType = MediaType.parseMediaType("image/webp");
            } else {
                contentType = MediaType.APPLICATION_OCTET_STREAM;
            }
            return ResponseEntity.ok()
                    .contentType(contentType)
                    .body(resource.getContentAsByteArray());
        } catch (Exception e) {
            try {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG)
                        .body(PLACEHOLDER.getContentAsByteArray());
            } catch (Exception ex) {
                return ResponseEntity.ok().build();
            }
        }
    }
}

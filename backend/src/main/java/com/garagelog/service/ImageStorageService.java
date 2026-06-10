package com.garagelog.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class ImageStorageService {

    @Value("${app.upload.dir}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        // Prioritizar ubicación relativa al JAR/classes (funciona con mvn spring-boot:run)
        Path appBase = resolveAppBase();
        Path candidate = appBase.resolve("uploads").normalize();
        try {
            Files.createDirectories(candidate);
            uploadPath = candidate;
            return;
        } catch (IOException ignored) {
            // fallback: usar uploadDir configurado
        }
        uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(uploadPath);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo crear el directorio de subida: " + uploadPath, e);
        }
    }

    private Path resolveAppBase() {
        try {
            String codeLocation = getClass().getProtectionDomain()
                    .getCodeSource().getLocation().toURI().getPath();
            Path dir = new File(codeLocation).getParentFile().toPath().toAbsolutePath().normalize();
            if (dir.endsWith("classes")) {
                dir = dir.getParent();
            }
            if (dir.endsWith("target")) {
                dir = dir.getParent();
            }
            return dir;
        } catch (Exception e) {
            return Paths.get(uploadDir).toAbsolutePath().normalize().getParent();
        }
    }

    public String store(MultipartFile file) {
        return store(file, UUID.randomUUID().toString());
    }

    public String store(MultipartFile file, String baseName) {
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String filename = baseName.replaceAll("[^a-zA-Z0-9._-]", "_") + extension;
        try {
            Path target = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
            return filename;
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen: " + filename, e);
        }
    }

    public Resource load(String filename) {
        try {
            Path file = uploadPath.resolve(filename).normalize();
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() && resource.isReadable()) {
                return resource;
            }
            throw new RuntimeException("Imagen no encontrada: " + filename);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error al leer la imagen: " + filename, e);
        }
    }

    public void delete(String filename) {
        try {
            Path file = uploadPath.resolve(filename).normalize();
            Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error al eliminar la imagen: " + filename, e);
        }
    }
}

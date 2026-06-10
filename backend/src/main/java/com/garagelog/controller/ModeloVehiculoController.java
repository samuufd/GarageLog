package com.garagelog.controller;

import com.garagelog.entity.ModeloVehiculo;
import com.garagelog.service.ImageStorageService;
import com.garagelog.service.ModeloVehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/garagelog/modelos")
public class ModeloVehiculoController {

    private final ModeloVehiculoService service;
    private final ImageStorageService imageStorageService;

    public ModeloVehiculoController(ModeloVehiculoService service, ImageStorageService imageStorageService) {
        this.service = service;
        this.imageStorageService = imageStorageService;
    }

    @GetMapping
    public ResponseEntity<List<ModeloVehiculo>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloVehiculo> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<ModeloVehiculo> create(@RequestBody ModeloVehiculo modelo) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(modelo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloVehiculo> update(@PathVariable Integer id, @RequestBody ModeloVehiculo modelo) {
        return ResponseEntity.ok(service.update(id, modelo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/imagen")
    public ResponseEntity<ModeloVehiculo> uploadImage(@PathVariable Integer id,
                                                       @RequestParam("file") MultipartFile file) {
        ModeloVehiculo modelo = service.findById(id);
        String baseName = modelo.getMarca() + "_" + modelo.getModelo() + "_" + modelo.getAño();
        String filename = imageStorageService.store(file, baseName);
        ModeloVehiculo updated = service.updateImage(id, filename);
        return ResponseEntity.ok(updated);
    }
}

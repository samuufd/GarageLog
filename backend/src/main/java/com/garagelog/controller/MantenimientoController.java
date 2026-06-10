package com.garagelog.controller;

import com.garagelog.dto.MantenimientoRequest;
import com.garagelog.entity.Mantenimiento;
import com.garagelog.service.MantenimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garagelog/mantenimientos")
public class MantenimientoController {

    private final MantenimientoService service;

    public MantenimientoController(MantenimientoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Mantenimiento>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mantenimiento> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/vehiculo/{idVehiculo}")
    public ResponseEntity<List<Mantenimiento>> getByVehiculo(@PathVariable Integer idVehiculo) {
        return ResponseEntity.ok(service.findByVehiculo(idVehiculo));
    }

    @PostMapping
    public ResponseEntity<Mantenimiento> create(@RequestBody MantenimientoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mantenimiento> update(@PathVariable Integer id, @RequestBody MantenimientoRequest request) {
        return ResponseEntity.ok(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

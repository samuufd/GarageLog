package com.garagelog.controller;

import com.garagelog.entity.Pieza;
import com.garagelog.service.PiezaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garagelog/piezas")
public class PiezaController {

    private final PiezaService service;

    public PiezaController(PiezaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Pieza>> getAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pieza> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    public ResponseEntity<Pieza> create(@RequestBody Pieza pieza) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(pieza));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pieza> update(@PathVariable Integer id, @RequestBody Pieza pieza) {
        return ResponseEntity.ok(service.update(id, pieza));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

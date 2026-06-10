package com.garagelog.service;

import com.garagelog.entity.Pieza;
import com.garagelog.repository.PiezaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PiezaService {

    private final PiezaRepository repository;

    public PiezaService(PiezaRepository repository) {
        this.repository = repository;
    }

    public List<Pieza> findAll() {
        return repository.findAll();
    }

    public Pieza findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pieza no encontrada: " + id));
    }

    @Transactional
    public Pieza save(Pieza pieza) {
        repository.insert(
                pieza.getNombre(),
                pieza.getDescripcion(),
                pieza.getPrecio()
        );
        Integer id = repository.getLastInsertedId();
        return findById(id);
    }

    @Transactional
    public Pieza update(Integer id, Pieza pieza) {
        repository.update(
                id,
                pieza.getNombre(),
                pieza.getDescripcion(),
                pieza.getPrecio()
        );
        return findById(id);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}

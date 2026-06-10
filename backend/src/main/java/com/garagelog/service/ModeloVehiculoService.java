package com.garagelog.service;

import com.garagelog.entity.ModeloVehiculo;
import com.garagelog.repository.ModeloVehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ModeloVehiculoService {

    private final ModeloVehiculoRepository repository;

    public ModeloVehiculoService(ModeloVehiculoRepository repository) {
        this.repository = repository;
    }

    public List<ModeloVehiculo> findAll() {
        return repository.findAll();
    }

    public ModeloVehiculo findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModeloVehiculo no encontrado: " + id));
    }

    @Transactional
    public ModeloVehiculo save(ModeloVehiculo modelo) {
        repository.insert(
                modelo.getMarca(),
                modelo.getModelo(),
                modelo.getAño(),
                modelo.getCombustible(),
                modelo.getRutaImagen()
        );
        Integer id = repository.getLastInsertedId();
        return findById(id);
    }

    @Transactional
    public ModeloVehiculo update(Integer id, ModeloVehiculo modelo) {
        repository.update(
                id,
                modelo.getMarca(),
                modelo.getModelo(),
                modelo.getAño(),
                modelo.getCombustible()
        );
        ModeloVehiculo existing = findById(id);
        if (modelo.getRutaImagen() != null) {
            existing.setRutaImagen(modelo.getRutaImagen());
            repository.save(existing);
        }
        return findById(id);
    }

    @Transactional
    public ModeloVehiculo updateImage(Integer id, String rutaImagen) {
        ModeloVehiculo existing = findById(id);
        existing.setRutaImagen(rutaImagen);
        repository.save(existing);
        return findById(id);
    }

    @Transactional
    public void delete(Integer id) {
        repository.deleteById(id);
    }
}

package com.garagelog.service;

import com.garagelog.dto.VehiculoRequest;
import com.garagelog.entity.Vehiculo;
import com.garagelog.repository.VehiculoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;

    public VehiculoService(VehiculoRepository vehiculoRepository) {
        this.vehiculoRepository = vehiculoRepository;
    }

    public List<Vehiculo> findAll() {
        return vehiculoRepository.findAll();
    }

    public Vehiculo findById(Integer id) {
        return vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado: " + id));
    }

    public List<Vehiculo> searchByText(String texto) {
        return vehiculoRepository.searchByText(texto);
    }

    @Transactional
    public Vehiculo create(VehiculoRequest request) {
        vehiculoRepository.insert(
                request.getMatricula(),
                request.getKmIniciales(),
                request.getFechaCompra(),
                request.getNotas(),
                request.getIdModelo()
        );
        return vehiculoRepository.findByMatricula(request.getMatricula())
                .orElseThrow(() -> new RuntimeException("Error al crear vehículo"));
    }

    @Transactional
    public Vehiculo update(Integer id, VehiculoRequest request) {
        Vehiculo existing = findById(id);
        vehiculoRepository.update(
                id,
                request.getMatricula() != null ? request.getMatricula() : existing.getMatricula(),
                request.getKmIniciales() != null ? request.getKmIniciales() : existing.getKmIniciales(),
                request.getFechaCompra() != null ? request.getFechaCompra() : existing.getFechaCompra(),
                request.getNotas() != null ? request.getNotas() : existing.getNotas(),
                request.getIdModelo() != null ? request.getIdModelo() : existing.getModeloVehiculo().getIdModelo()
        );
        return findById(id);
    }

    @Transactional
    public void delete(Integer id) {
        vehiculoRepository.deleteById(id);
    }
}

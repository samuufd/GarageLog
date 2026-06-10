package com.garagelog.service;

import com.garagelog.dto.MantenimientoRequest;
import com.garagelog.entity.Mantenimiento;
import com.garagelog.entity.Pieza;
import com.garagelog.repository.MantenimientoPiezaRepository;
import com.garagelog.repository.MantenimientoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MantenimientoService {

    private final MantenimientoRepository repository;
    private final MantenimientoPiezaRepository piezaRepository;
    private final VehiculoService vehiculoService;
    private final PiezaService piezaService;

    public MantenimientoService(MantenimientoRepository repository,
                                MantenimientoPiezaRepository piezaRepository,
                                VehiculoService vehiculoService,
                                PiezaService piezaService) {
        this.repository = repository;
        this.piezaRepository = piezaRepository;
        this.vehiculoService = vehiculoService;
        this.piezaService = piezaService;
    }

    public List<Mantenimiento> findAll() {
        return repository.findAll();
    }

    public Mantenimiento findById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado: " + id));
    }

    public List<Mantenimiento> findByVehiculo(Integer idVehiculo) {
        return repository.findByVehiculoId(idVehiculo);
    }

    @Transactional
    public Mantenimiento create(MantenimientoRequest request) {
        vehiculoService.findById(request.getIdVehiculo());

        repository.insert(
                request.getIdVehiculo(),
                request.getFecha(),
                request.getKm(),
                request.getCosteTotal(),
                request.getObservaciones()
        );

        Integer mantenimientoId = repository.getLastInsertedId();

        if (request.getPiezas() != null) {
            for (MantenimientoRequest.MantenimientoPiezaDto dto : request.getPiezas()) {
                Pieza pieza = piezaService.findById(dto.getIdPieza());
                piezaRepository.insert(
                        mantenimientoId,
                        pieza.getIdPieza(),
                        dto.getCantidad(),
                        dto.getPrecioUnitario(),
                        dto.getObservaciones()
                );
            }
        }

        return repository.findById(mantenimientoId)
                .orElseThrow(() -> new RuntimeException("Error al crear mantenimiento"));
    }

    @Transactional
    public Mantenimiento update(Integer id, MantenimientoRequest request) {
        repository.update(
                id,
                request.getFecha(),
                request.getKm(),
                request.getCosteTotal(),
                request.getObservaciones()
        );

        piezaRepository.deleteByMantenimientoId(id);

        if (request.getPiezas() != null) {
            for (MantenimientoRequest.MantenimientoPiezaDto dto : request.getPiezas()) {
                Pieza pieza = piezaService.findById(dto.getIdPieza());
                piezaRepository.insert(
                        id,
                        pieza.getIdPieza(),
                        dto.getCantidad(),
                        dto.getPrecioUnitario(),
                        dto.getObservaciones()
                );
            }
        }

        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Mantenimiento no encontrado: " + id));
    }

    @Transactional
    public void delete(Integer id) {
        piezaRepository.deleteByMantenimientoId(id);
        repository.deleteById(id);
    }
}

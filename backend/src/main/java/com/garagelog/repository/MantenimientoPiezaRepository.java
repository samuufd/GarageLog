package com.garagelog.repository;

import com.garagelog.entity.MantenimientoPieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface MantenimientoPiezaRepository extends JpaRepository<MantenimientoPieza, Integer> {

    @Override
    @Query("SELECT mp FROM MantenimientoPieza mp JOIN FETCH mp.pieza")
    List<MantenimientoPieza> findAll();

    @Override
    @Query("SELECT mp FROM MantenimientoPieza mp JOIN FETCH mp.pieza WHERE mp.idMantenimientoPieza = :id")
    Optional<MantenimientoPieza> findById(@Param("id") Integer id);

    @Override
    @Modifying
    @Query("DELETE FROM MantenimientoPieza mp WHERE mp.idMantenimientoPieza = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO mantenimiento_pieza (id_mantenimiento, id_pieza, cantidad, precio_unitario, observaciones) "
                 + "VALUES (:idMantenimiento, :idPieza, :cantidad, :precioUnitario, :observaciones)", nativeQuery = true)
    void insert(@Param("idMantenimiento") Integer idMantenimiento,
                  @Param("idPieza") Integer idPieza,
                  @Param("cantidad") Integer cantidad,
                  @Param("precioUnitario") BigDecimal precioUnitario,
                  @Param("observaciones") String observaciones);

    @Modifying
    @Query("UPDATE MantenimientoPieza mp SET mp.cantidad = :cantidad, "
         + "mp.precioUnitario = :precioUnitario, mp.observaciones = :observaciones WHERE mp.idMantenimientoPieza = :id")
    int update(@Param("id") Integer id,
                   @Param("cantidad") Integer cantidad,
                   @Param("precioUnitario") BigDecimal precioUnitario,
                   @Param("observaciones") String observaciones);

    @Modifying
    @Query("DELETE FROM MantenimientoPieza mp WHERE mp.mantenimiento.idMantenimiento = :idMantenimiento")
    void deleteByMantenimientoId(@Param("idMantenimiento") Integer idMantenimiento);
}

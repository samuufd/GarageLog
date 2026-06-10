package com.garagelog.repository;

import com.garagelog.entity.Mantenimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Integer> {

    @Override
    @Query("SELECT m FROM Mantenimiento m JOIN FETCH m.vehiculo")
    List<Mantenimiento> findAll();

    @Override
    @Query("SELECT m FROM Mantenimiento m JOIN FETCH m.vehiculo WHERE m.idMantenimiento = :id")
    Optional<Mantenimiento> findById(@Param("id") Integer id);

    @Override
    @Modifying
    @Query("DELETE FROM Mantenimiento m WHERE m.idMantenimiento = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO mantenimiento (id_vehiculo, fecha, km, coste_total, observaciones) "
                 + "VALUES (:idVehiculo, :fecha, :km, :costeTotal, :observaciones)", nativeQuery = true)
    void insert(@Param("idVehiculo") Integer idVehiculo,
                  @Param("fecha") LocalDate fecha,
                  @Param("km") Integer km,
                  @Param("costeTotal") BigDecimal costeTotal,
                  @Param("observaciones") String observaciones);

    @Modifying
    @Query("UPDATE Mantenimiento m SET m.fecha = :fecha, m.km = :km, "
         + "m.costeTotal = :costeTotal, m.observaciones = :observaciones WHERE m.idMantenimiento = :id")
    int update(@Param("id") Integer id,
                   @Param("fecha") LocalDate fecha,
                   @Param("km") Integer km,
                   @Param("costeTotal") BigDecimal costeTotal,
                   @Param("observaciones") String observaciones);

    @Query("SELECT m FROM Mantenimiento m JOIN FETCH m.vehiculo WHERE m.vehiculo.idVehiculo = :idVehiculo ORDER BY m.fecha DESC")
    List<Mantenimiento> findByVehiculoId(@Param("idVehiculo") Integer idVehiculo);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer getLastInsertedId();
}

package com.garagelog.repository;

import com.garagelog.entity.ModeloVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ModeloVehiculoRepository extends JpaRepository<ModeloVehiculo, Integer> {

    @Override
    @Query("SELECT mv FROM ModeloVehiculo mv")
    List<ModeloVehiculo> findAll();

    @Override
    @Query("SELECT mv FROM ModeloVehiculo mv WHERE mv.idModelo = :id")
    Optional<ModeloVehiculo> findById(@Param("id") Integer id);

    @Override
    @Modifying
    @Query("DELETE FROM ModeloVehiculo mv WHERE mv.idModelo = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO modelo_vehiculo (marca, modelo, año, combustible, ruta_imagen) "
                 + "VALUES (:marca, :modelo, :año, :combustible, :rutaImagen)", nativeQuery = true)
    void insert(@Param("marca") String marca,
                  @Param("modelo") String modelo,
                  @Param("año") Integer año,
                  @Param("combustible") String combustible,
                  @Param("rutaImagen") String rutaImagen);

    @Modifying
    @Query("UPDATE ModeloVehiculo mv SET mv.marca = :marca, mv.modelo = :modelo, "
         + "mv.año = :año, mv.combustible = :combustible WHERE mv.idModelo = :id")
    int update(@Param("id") Integer id,
                   @Param("marca") String marca,
                   @Param("modelo") String modelo,
                   @Param("año") Integer año,
                   @Param("combustible") String combustible);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer getLastInsertedId();
}

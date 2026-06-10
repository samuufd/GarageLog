package com.garagelog.repository;

import com.garagelog.entity.Pieza;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PiezaRepository extends JpaRepository<Pieza, Integer> {

    @Override
    @Query("SELECT p FROM Pieza p")
    List<Pieza> findAll();

    @Override
    @Query("SELECT p FROM Pieza p WHERE p.idPieza = :id")
    Optional<Pieza> findById(@Param("id") Integer id);

    @Override
    @Modifying
    @Query("DELETE FROM Pieza p WHERE p.idPieza = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO pieza (nombre, descripcion, precio) "
                 + "VALUES (:nombre, :descripcion, :precio)", nativeQuery = true)
    void insert(@Param("nombre") String nombre,
                  @Param("descripcion") String descripcion,
                  @Param("precio") BigDecimal precio);

    @Modifying
    @Query("UPDATE Pieza p SET p.nombre = :nombre, p.descripcion = :descripcion, "
         + "p.precio = :precio WHERE p.idPieza = :id")
    int update(@Param("id") Integer id,
                   @Param("nombre") String nombre,
                   @Param("descripcion") String descripcion,
                   @Param("precio") BigDecimal precio);

    @Query(value = "SELECT LAST_INSERT_ID()", nativeQuery = true)
    Integer getLastInsertedId();
}

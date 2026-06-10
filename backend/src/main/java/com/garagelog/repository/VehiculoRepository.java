package com.garagelog.repository;

import com.garagelog.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Integer> {

    @Override
    @Query("SELECT v FROM Vehiculo v JOIN FETCH v.modeloVehiculo")
    List<Vehiculo> findAll();

    @Override
    @Query("SELECT v FROM Vehiculo v JOIN FETCH v.modeloVehiculo WHERE v.idVehiculo = :id")
    Optional<Vehiculo> findById(@Param("id") Integer id);

    @Override
    @Modifying
    @Query("DELETE FROM Vehiculo v WHERE v.idVehiculo = :id")
    void deleteById(@Param("id") Integer id);

    @Modifying
    @Query(value = "INSERT INTO vehiculo (matricula, km_iniciales, fecha_compra, notas, id_modelo) "
                 + "VALUES (:matricula, :kmIniciales, :fechaCompra, :notas, :idModelo)", nativeQuery = true)
    void insert(@Param("matricula") String matricula,
                  @Param("kmIniciales") Integer kmIniciales,
                  @Param("fechaCompra") LocalDate fechaCompra,
                  @Param("notas") String notas,
                  @Param("idModelo") Integer idModelo);

    @Modifying
    @Query(value = "UPDATE vehiculo SET matricula = :matricula, km_iniciales = :kmIniciales, "
         + "fecha_compra = :fechaCompra, notas = :notas, id_modelo = :idModelo WHERE id_vehiculo = :id",
         nativeQuery = true)
    int update(@Param("id") Integer id,
                   @Param("matricula") String matricula,
                   @Param("kmIniciales") Integer kmIniciales,
                   @Param("fechaCompra") LocalDate fechaCompra,
                   @Param("notas") String notas,
                   @Param("idModelo") Integer idModelo);

    @Query("SELECT v FROM Vehiculo v JOIN FETCH v.modeloVehiculo WHERE v.matricula = :matricula")
    Optional<Vehiculo> findByMatricula(@Param("matricula") String matricula);

    @Query("SELECT v FROM Vehiculo v JOIN FETCH v.modeloVehiculo mv "
         + "WHERE LOWER(v.matricula) LIKE LOWER(CONCAT('%', :texto, '%')) "
         + "OR LOWER(mv.marca) LIKE LOWER(CONCAT('%', :texto, '%')) "
         + "OR LOWER(mv.modelo) LIKE LOWER(CONCAT('%', :texto, '%')) "
         + "OR LOWER(CONCAT(mv.marca, ' ', mv.modelo)) LIKE LOWER(CONCAT('%', :texto, '%'))")
    List<Vehiculo> searchByText(@Param("texto") String texto);
}

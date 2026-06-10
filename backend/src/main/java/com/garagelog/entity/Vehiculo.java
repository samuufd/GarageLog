package com.garagelog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "vehiculo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Integer idVehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_modelo")
    @JsonIgnoreProperties("vehiculos")
    private ModeloVehiculo modeloVehiculo;

    @Column(nullable = false, length = 20)
    private String matricula;

    @Column(name = "km_iniciales")
    private Integer kmIniciales;

    @Column(name = "fecha_compra")
    private LocalDate fechaCompra;

    @Column(columnDefinition = "TEXT")
    private String notas;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("vehiculo")
    private List<Mantenimiento> mantenimientos;

    public Integer getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Integer idVehiculo) { this.idVehiculo = idVehiculo; }
    public ModeloVehiculo getModeloVehiculo() { return modeloVehiculo; }
    public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) { this.modeloVehiculo = modeloVehiculo; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public Integer getKmIniciales() { return kmIniciales; }
    public void setKmIniciales(Integer kmIniciales) { this.kmIniciales = kmIniciales; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public List<Mantenimiento> getMantenimientos() { return mantenimientos; }
    public void setMantenimientos(List<Mantenimiento> mantenimientos) { this.mantenimientos = mantenimientos; }
}

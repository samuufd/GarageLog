package com.garagelog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pieza")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pieza")
    private Integer idPieza;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(precision = 10, scale = 2)
    private BigDecimal precio;

    @OneToMany(mappedBy = "pieza", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("pieza")
    private List<MantenimientoPieza> mantenimientos;

    public Integer getIdPieza() { return idPieza; }
    public void setIdPieza(Integer idPieza) { this.idPieza = idPieza; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public BigDecimal getPrecio() { return precio; }
    public void setPrecio(BigDecimal precio) { this.precio = precio; }
    public List<MantenimientoPieza> getMantenimientos() { return mantenimientos; }
    public void setMantenimientos(List<MantenimientoPieza> mantenimientos) { this.mantenimientos = mantenimientos; }
}

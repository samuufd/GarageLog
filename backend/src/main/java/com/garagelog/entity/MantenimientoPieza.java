package com.garagelog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "mantenimiento_pieza")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MantenimientoPieza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento_pieza")
    private Integer idMantenimientoPieza;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_mantenimiento")
    @JsonIgnoreProperties("piezas")
    private Mantenimiento mantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pieza")
    @JsonIgnoreProperties("mantenimientos")
    private Pieza pieza;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    public Integer getIdMantenimientoPieza() { return idMantenimientoPieza; }
    public void setIdMantenimientoPieza(Integer idMantenimientoPieza) { this.idMantenimientoPieza = idMantenimientoPieza; }
    public Mantenimiento getMantenimiento() { return mantenimiento; }
    public void setMantenimiento(Mantenimiento mantenimiento) { this.mantenimiento = mantenimiento; }
    public Pieza getPieza() { return pieza; }
    public void setPieza(Pieza pieza) { this.pieza = pieza; }
    public Integer getCantidad() { return cantidad; }
    public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
    public BigDecimal getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}

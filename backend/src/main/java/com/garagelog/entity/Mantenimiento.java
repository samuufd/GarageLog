package com.garagelog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "mantenimiento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Mantenimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mantenimiento")
    private Integer idMantenimiento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_vehiculo")
    @JsonIgnoreProperties("mantenimientos")
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private LocalDate fecha;

    private Integer km;

    @Column(name = "coste_total", precision = 10, scale = 2)
    private BigDecimal costeTotal;

    @Column(columnDefinition = "TEXT")
    private String observaciones;

    @OneToMany(mappedBy = "mantenimiento", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("mantenimiento")
    private List<MantenimientoPieza> piezas;

    public Integer getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(Integer idMantenimiento) { this.idMantenimiento = idMantenimiento; }
    public Vehiculo getVehiculo() { return vehiculo; }
    public void setVehiculo(Vehiculo vehiculo) { this.vehiculo = vehiculo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Integer getKm() { return km; }
    public void setKm(Integer km) { this.km = km; }
    public BigDecimal getCosteTotal() { return costeTotal; }
    public void setCosteTotal(BigDecimal costeTotal) { this.costeTotal = costeTotal; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public List<MantenimientoPieza> getPiezas() { return piezas; }
    public void setPiezas(List<MantenimientoPieza> piezas) { this.piezas = piezas; }
}

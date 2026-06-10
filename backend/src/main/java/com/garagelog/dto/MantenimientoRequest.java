package com.garagelog.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class MantenimientoRequest {
    private Integer idVehiculo;
    private LocalDate fecha;
    private Integer km;
    private BigDecimal costeTotal;
    private String observaciones;
    private List<MantenimientoPiezaDto> piezas;

    public Integer getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(Integer idVehiculo) { this.idVehiculo = idVehiculo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public Integer getKm() { return km; }
    public void setKm(Integer km) { this.km = km; }
    public BigDecimal getCosteTotal() { return costeTotal; }
    public void setCosteTotal(BigDecimal costeTotal) { this.costeTotal = costeTotal; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public List<MantenimientoPiezaDto> getPiezas() { return piezas; }
    public void setPiezas(List<MantenimientoPiezaDto> piezas) { this.piezas = piezas; }

    public static class MantenimientoPiezaDto {
        private Integer idPieza;
        private Integer cantidad;
        private BigDecimal precioUnitario;
        private String observaciones;

        public Integer getIdPieza() { return idPieza; }
        public void setIdPieza(Integer idPieza) { this.idPieza = idPieza; }
        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }
        public BigDecimal getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(BigDecimal precioUnitario) { this.precioUnitario = precioUnitario; }
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }
}

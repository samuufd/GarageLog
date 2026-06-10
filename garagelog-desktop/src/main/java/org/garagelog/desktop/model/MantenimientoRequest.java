package org.garagelog.desktop.model;

import java.util.List;

public class MantenimientoRequest {
    private int idVehiculo;
    private String fecha;
    private int km;
    private double costeTotal;
    private double manoObra;
    private String observaciones;
    private List<PiezaRequest> piezas;

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public int getKm() { return km; }
    public void setKm(int km) { this.km = km; }
    public double getCosteTotal() { return costeTotal; }
    public void setCosteTotal(double costeTotal) { this.costeTotal = costeTotal; }
    public double getManoObra() { return manoObra; }
    public void setManoObra(double manoObra) { this.manoObra = manoObra; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public List<PiezaRequest> getPiezas() { return piezas; }
    public void setPiezas(List<PiezaRequest> piezas) { this.piezas = piezas; }

    public static class PiezaRequest {
        private int idPieza;
        private int cantidad;
        private double precioUnitario;
        private String observaciones;

        public int getIdPieza() { return idPieza; }
        public void setIdPieza(int idPieza) { this.idPieza = idPieza; }
        public int getCantidad() { return cantidad; }
        public void setCantidad(int cantidad) { this.cantidad = cantidad; }
        public double getPrecioUnitario() { return precioUnitario; }
        public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
        public String getObservaciones() { return observaciones; }
        public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    }
}

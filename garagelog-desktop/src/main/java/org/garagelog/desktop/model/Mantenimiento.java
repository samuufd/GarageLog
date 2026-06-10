package org.garagelog.desktop.model;

import java.util.List;

public class Mantenimiento {
    private int idMantenimiento;
    private int idVehiculo;
    private String fecha;
    private int km;
    private double costeTotal;
    private double manoObra;
    private String observaciones;
    private List<MantenimientoPieza> piezas;

    public int getIdMantenimiento() { return idMantenimiento; }
    public void setIdMantenimiento(int idMantenimiento) { this.idMantenimiento = idMantenimiento; }
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
    public List<MantenimientoPieza> getPiezas() { return piezas; }
    public void setPiezas(List<MantenimientoPieza> piezas) { this.piezas = piezas; }
}

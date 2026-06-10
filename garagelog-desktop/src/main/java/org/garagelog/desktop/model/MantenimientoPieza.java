package org.garagelog.desktop.model;

public class MantenimientoPieza {
    private int idMantenimientoPieza;
    private int cantidad;
    private double precioUnitario;
    private String observaciones;
    private Pieza pieza;
    private Mantenimiento mantenimiento;

    public int getIdMantenimientoPieza() { return idMantenimientoPieza; }
    public void setIdMantenimientoPieza(int idMantenimientoPieza) { this.idMantenimientoPieza = idMantenimientoPieza; }
    public int getIdPieza() { return pieza != null ? pieza.getIdPieza() : 0; }
    public void setIdPieza(int idPieza) {
        if (this.pieza == null) this.pieza = new Pieza();
        this.pieza.setIdPieza(idPieza);
    }
    public String getNombrePieza() { return pieza != null ? pieza.getNombre() : ""; }
    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public Pieza getPieza() { return pieza; }
    public void setPieza(Pieza pieza) { this.pieza = pieza; }
}

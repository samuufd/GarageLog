package org.garagelog.desktop.model;

public class Pieza {
    private int idPieza;
    private String nombre;
    private String descripcion;
    private double precio;

    public int getIdPieza() { return idPieza; }
    public void setIdPieza(int idPieza) { this.idPieza = idPieza; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }

    @Override
    public String toString() { return nombre; }
}

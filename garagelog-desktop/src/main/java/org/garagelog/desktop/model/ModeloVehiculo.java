package org.garagelog.desktop.model;

public class ModeloVehiculo {
    private Integer idModelo;
    private String marca;
    private String modelo;
    private int año;
    private String combustible;
    private String rutaImagen;

    public Integer getIdModelo() { return idModelo; }
    public void setIdModelo(Integer idModelo) { this.idModelo = idModelo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public int getAño() { return año; }
    public void setAño(int año) { this.año = año; }
    public String getCombustible() { return combustible; }
    public void setCombustible(String combustible) { this.combustible = combustible; }
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }

    @Override
    public String toString() { return marca + " " + modelo + " (" + año + ")"; }
}

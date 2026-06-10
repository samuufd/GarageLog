package org.garagelog.desktop.model;

public class Vehiculo {
    private int idVehiculo;
    private int idModelo;
    private String matricula;
    private int kmIniciales;
    private String fechaCompra;
    private String notas;
    private ModeloVehiculo modeloVehiculo;

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }
    public int getIdModelo() { return idModelo; }
    public void setIdModelo(int idModelo) { this.idModelo = idModelo; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public int getKmIniciales() { return kmIniciales; }
    public void setKmIniciales(int kmIniciales) { this.kmIniciales = kmIniciales; }
    public String getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(String fechaCompra) { this.fechaCompra = fechaCompra; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
    public ModeloVehiculo getModeloVehiculo() { return modeloVehiculo; }
    public void setModeloVehiculo(ModeloVehiculo modeloVehiculo) { this.modeloVehiculo = modeloVehiculo; }

    @Override
    public String toString() { return matricula; }
}

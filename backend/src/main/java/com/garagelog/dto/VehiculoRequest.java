package com.garagelog.dto;

import java.time.LocalDate;

public class VehiculoRequest {
    private Integer idModelo;
    private String matricula;
    private Integer kmIniciales;
    private LocalDate fechaCompra;
    private String notas;

    public Integer getIdModelo() { return idModelo; }
    public void setIdModelo(Integer idModelo) { this.idModelo = idModelo; }
    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }
    public Integer getKmIniciales() { return kmIniciales; }
    public void setKmIniciales(Integer kmIniciales) { this.kmIniciales = kmIniciales; }
    public LocalDate getFechaCompra() { return fechaCompra; }
    public void setFechaCompra(LocalDate fechaCompra) { this.fechaCompra = fechaCompra; }
    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}

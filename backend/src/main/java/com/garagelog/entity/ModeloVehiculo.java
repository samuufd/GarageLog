package com.garagelog.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "modelo_vehiculo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ModeloVehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_modelo")
    private Integer idModelo;

    @Column(nullable = false, length = 50)
    private String marca;

    @Column(nullable = false, length = 50)
    private String modelo;

    private Integer año;

    @Column(length = 30)
    private String combustible;

    @Column(name = "ruta_imagen", length = 255)
    private String rutaImagen;

    @OneToMany(mappedBy = "modeloVehiculo")
    @JsonIgnoreProperties("modeloVehiculo")
    private List<Vehiculo> vehiculos;

    public Integer getIdModelo() { return idModelo; }
    public void setIdModelo(Integer idModelo) { this.idModelo = idModelo; }
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public Integer getAño() { return año; }
    public void setAño(Integer año) { this.año = año; }
    public String getCombustible() { return combustible; }
    public void setCombustible(String combustible) { this.combustible = combustible; }
    public String getRutaImagen() { return rutaImagen; }
    public void setRutaImagen(String rutaImagen) { this.rutaImagen = rutaImagen; }
    public List<Vehiculo> getVehiculos() { return vehiculos; }
    public void setVehiculos(List<Vehiculo> vehiculos) { this.vehiculos = vehiculos; }
}

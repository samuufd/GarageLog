package org.garagelog.desktop.api;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;
import org.garagelog.desktop.model.*;

public class ApiClient {

    public static final String BASE_URL = "http://localhost:8080/garagelog";
    private final HttpClient http;
    private final Gson gson;

    public ApiClient() {
        this.http = HttpClient.newHttpClient();
        this.gson = new Gson();
    }

    private String get(String path) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .GET()
                .build();
            HttpResponse<String> res = http.send(
                req,
                HttpResponse.BodyHandlers.ofString()
            );
            return res.body();
        } catch (Exception e) {
            throw new RuntimeException("GET " + path + " failed", e);
        }
    }

    private String post(String path, Object body) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                .build();
            HttpResponse<String> res = http.send(
                req,
                HttpResponse.BodyHandlers.ofString()
            );
            return res.body();
        } catch (Exception e) {
            throw new RuntimeException("POST " + path + " failed", e);
        }
    }

    private String put(String path, Object body) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(body)))
                .build();
            HttpResponse<String> res = http.send(
                req,
                HttpResponse.BodyHandlers.ofString()
            );
            return res.body();
        } catch (Exception e) {
            throw new RuntimeException("PUT " + path + " failed", e);
        }
    }

    private String delete(String path) {
        try {
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + path))
                .DELETE()
                .build();
            HttpResponse<String> res = http.send(
                req,
                HttpResponse.BodyHandlers.ofString()
            );
            return res.body();
        } catch (Exception e) {
            throw new RuntimeException("DELETE " + path + " failed", e);
        }
    }

    // --- Modelos ---
    public List<ModeloVehiculo> getModelos() {
        String json = get("/modelos");
        Type t = new TypeToken<List<ModeloVehiculo>>() {}.getType();
        return gson.fromJson(json, t);
    }

    public ModeloVehiculo getModelo(int id) {
        String json = get("/modelos/" + id);
        return gson.fromJson(json, ModeloVehiculo.class);
    }

    public ModeloVehiculo createModelo(ModeloVehiculo m) {
        String json = post("/modelos", m);
        return gson.fromJson(json, ModeloVehiculo.class);
    }

    public ModeloVehiculo updateModelo(int id, ModeloVehiculo m) {
        String json = put("/modelos/" + id, m);
        return gson.fromJson(json, ModeloVehiculo.class);
    }

    public void deleteModelo(int id) {
        delete("/modelos/" + id);
    }

    public ModeloVehiculo uploadModeloImage(int modeloId, Path imagePath) {
        try {
            String boundary = UUID.randomUUID().toString();
            String filename = imagePath.getFileName().toString();
            byte[] fileBytes = Files.readAllBytes(imagePath);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            baos.write(("--" + boundary + "\r\n").getBytes());
            baos.write(
                (
                    "Content-Disposition: form-data; name=\"file\"; filename=\"" +
                    filename +
                    "\"\r\n"
                ).getBytes()
            );
            baos.write(
                "Content-Type: application/octet-stream\r\n\r\n".getBytes()
            );
            baos.write(fileBytes);
            baos.write(("\r\n--" + boundary + "--\r\n").getBytes());

            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/modelos/" + modeloId + "/imagen"))
                .header(
                    "Content-Type",
                    "multipart/form-data; boundary=" + boundary
                )
                .POST(
                    HttpRequest.BodyPublishers.ofByteArray(baos.toByteArray())
                )
                .build();

            HttpResponse<String> res = http.send(
                req,
                HttpResponse.BodyHandlers.ofString()
            );
            return gson.fromJson(res.body(), ModeloVehiculo.class);
        } catch (IOException e) {
            throw new RuntimeException(
                "Error al leer la imagen: " + imagePath,
                e
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al subir imagen", e);
        }
    }

    // --- Vehiculos ---
    public List<Vehiculo> getVehiculos() {
        String json = get("/vehiculos");
        Type t = new TypeToken<List<Vehiculo>>() {}.getType();
        return gson.fromJson(json, t);
    }

    public Vehiculo getVehiculo(int id) {
        String json = get("/vehiculos/" + id);
        return gson.fromJson(json, Vehiculo.class);
    }

    public Vehiculo createVehiculo(Vehiculo v) {
        String json = post("/vehiculos", v);
        return gson.fromJson(json, Vehiculo.class);
    }

    public Vehiculo updateVehiculo(int id, Vehiculo v) {
        String json = put("/vehiculos/" + id, v);
        return gson.fromJson(json, Vehiculo.class);
    }

    public void deleteVehiculo(int id) {
        delete("/vehiculos/" + id);
    }

    // --- Piezas ---
    public List<Pieza> getPiezas() {
        String json = get("/piezas");
        Type t = new TypeToken<List<Pieza>>() {}.getType();
        return gson.fromJson(json, t);
    }

    public Pieza getPieza(int id) {
        String json = get("/piezas/" + id);
        return gson.fromJson(json, Pieza.class);
    }

    public Pieza createPieza(Pieza p) {
        String json = post("/piezas", p);
        return gson.fromJson(json, Pieza.class);
    }

    public Pieza updatePieza(int id, Pieza p) {
        String json = put("/piezas/" + id, p);
        return gson.fromJson(json, Pieza.class);
    }

    public void deletePieza(int id) {
        delete("/piezas/" + id);
    }

    // --- Mantenimientos ---
    public List<Mantenimiento> getMantenimientos() {
        String json = get("/mantenimientos");
        Type t = new TypeToken<List<Mantenimiento>>() {}.getType();
        return gson.fromJson(json, t);
    }

    public Mantenimiento getMantenimiento(int id) {
        String json = get("/mantenimientos/" + id);
        return gson.fromJson(json, Mantenimiento.class);
    }

    public List<Mantenimiento> getMantenimientosByVehiculo(int idVehiculo) {
        String json = get("/mantenimientos/vehiculo/" + idVehiculo);
        Type t = new TypeToken<List<Mantenimiento>>() {}.getType();
        return gson.fromJson(json, t);
    }

    public Mantenimiento createMantenimiento(MantenimientoRequest request) {
        String json = post("/mantenimientos", request);
        return gson.fromJson(json, Mantenimiento.class);
    }

    public void deleteMantenimiento(int id) {
        delete("/mantenimientos/" + id);
    }
}

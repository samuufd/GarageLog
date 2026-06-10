package org.garagelog.desktop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.ModeloVehiculo;
import org.garagelog.desktop.model.Vehiculo;

public class VehiculoFormController {

    @FXML private Label tituloLabel;
    @FXML private ComboBox<ModeloVehiculo> modeloCombo;
    @FXML private TextField matriculaField;
    @FXML private TextField kmField;
    @FXML private DatePicker fechaPicker;
    @FXML private TextArea notasArea;

    private final ApiClient api = new ApiClient();
    private Vehiculo vehiculoExistente;

    @FXML
    public void initialize() {
        cargarModelos();
    }

    public void setVehiculo(Vehiculo v) {
        this.vehiculoExistente = v;
        tituloLabel.setText("Editar Vehículo");
        ModeloVehiculo mv = v.getModeloVehiculo();
        if (mv != null) {
            modeloCombo.getSelectionModel().select(
                    modeloCombo.getItems().stream()
                            .filter(m -> m.getIdModelo().equals(mv.getIdModelo()))
                            .findFirst().orElse(null));
        }
        matriculaField.setText(v.getMatricula());
        kmField.setText(String.valueOf(v.getKmIniciales()));
        if (v.getFechaCompra() != null && !v.getFechaCompra().isEmpty()) {
            fechaPicker.setValue(java.time.LocalDate.parse(v.getFechaCompra()));
        }
        notasArea.setText(v.getNotas());
    }

    private void cargarModelos() {
        try {
            modeloCombo.setItems(FXCollections.observableArrayList(api.getModelos()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onGuardar() {
        ModeloVehiculo modelo = modeloCombo.getValue();
        if (modelo == null) {
            mostrarError("Selecciona un modelo");
            return;
        }
        String matricula = matriculaField.getText().trim();
        if (matricula.isEmpty()) {
            mostrarError("La matrícula es obligatoria");
            return;
        }
        String kmText = kmField.getText().trim();
        if (kmText.isEmpty()) {
            mostrarError("Los KM iniciales son obligatorios");
            return;
        }
        int km;
        try {
            km = Integer.parseInt(kmText);
        } catch (NumberFormatException e) {
            mostrarError("KM debe ser un número");
            return;
        }
        if (fechaPicker.getValue() == null) {
            mostrarError("Selecciona una fecha de compra");
            return;
        }

        try {
            Vehiculo v = new Vehiculo();
            if (vehiculoExistente != null) {
                v.setIdVehiculo(vehiculoExistente.getIdVehiculo());
            }
            v.setModeloVehiculo(modelo);
            v.setIdModelo(modelo.getIdModelo());
            v.setMatricula(matricula);
            v.setKmIniciales(km);
            v.setFechaCompra(fechaPicker.getValue().toString());
            v.setNotas(notasArea.getText().trim());

            if (vehiculoExistente != null) {
                System.out.println("ENVIANDO: " + new com.google.gson.Gson().toJson(v));
                api.updateVehiculo(vehiculoExistente.getIdVehiculo(), v);
            } else {
                api.createVehiculo(v);
            }
            cerrar();
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void onCancelar() {
        cerrar();
    }

    private void cerrar() {
        ((Stage) modeloCombo.getScene().getWindow()).close();
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

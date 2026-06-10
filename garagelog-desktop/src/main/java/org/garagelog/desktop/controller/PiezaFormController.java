package org.garagelog.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.Pieza;

public class PiezaFormController {

    @FXML private Label tituloLabel;
    @FXML private TextField nombreField;
    @FXML private TextArea descripcionArea;
    @FXML private TextField precioField;

    private final ApiClient api = new ApiClient();
    private Pieza piezaExistente;

    @FXML
    public void initialize() {}

    public void setPieza(Pieza p) {
        this.piezaExistente = p;
        tituloLabel.setText("Editar Pieza");
        nombreField.setText(p.getNombre());
        descripcionArea.setText(p.getDescripcion());
        precioField.setText(String.valueOf(p.getPrecio()));
    }

    @FXML
    private void onGuardar() {
        String nombre = nombreField.getText().trim();
        if (nombre.isEmpty()) { mostrarError("El nombre es obligatorio"); return; }
        String precioText = precioField.getText().trim();
        double precio;
        try {
            precio = Double.parseDouble(precioText);
        } catch (NumberFormatException e) {
            mostrarError("El precio debe ser un número"); return;
        }

        Pieza p = new Pieza();
        p.setNombre(nombre);
        p.setDescripcion(descripcionArea.getText().trim());
        p.setPrecio(precio);

        try {
            if (piezaExistente != null) {
                api.updatePieza(piezaExistente.getIdPieza(), p);
            } else {
                api.createPieza(p);
            }
            cerrar();
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void onCancelar() { cerrar(); }

    private void cerrar() { ((Stage) nombreField.getScene().getWindow()).close(); }

    private void mostrarError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}

package org.garagelog.desktop.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.ModeloVehiculo;

import java.io.File;

public class ModeloFormController {

    @FXML private Label tituloLabel;
    @FXML private TextField marcaField;
    @FXML private TextField modeloField;
    @FXML private TextField anoField;
    @FXML private ComboBox<String> combustibleCombo;
    @FXML private ImageView imagePreview;
    @FXML private Label imageStatusLabel;

    private final ApiClient api = new ApiClient();
    private ModeloVehiculo modeloExistente;
    private File selectedImageFile;

    @FXML
    public void initialize() {
        combustibleCombo.setItems(FXCollections.observableArrayList(
                "Gasolina", "Diésel", "Híbrido", "Eléctrico", "GLP"));
        imagePreview.setPreserveRatio(true);
    }

    public void setModelo(ModeloVehiculo m) {
        this.modeloExistente = m;
        tituloLabel.setText("Editar Modelo");
        marcaField.setText(m.getMarca());
        modeloField.setText(m.getModelo());
        anoField.setText(String.valueOf(m.getAño()));
        combustibleCombo.setValue(m.getCombustible());
        loadCurrentImage();
    }

    private void loadCurrentImage() {
        if (modeloExistente != null && modeloExistente.getRutaImagen() != null
                && !modeloExistente.getRutaImagen().isEmpty()) {
            String url = ApiClient.BASE_URL + "/imagenes/" + modeloExistente.getRutaImagen();
            try {
                Image img = new Image(url, 200, 150, true, true);
                imagePreview.setImage(img);
                imageStatusLabel.setText(modeloExistente.getRutaImagen());
            } catch (Exception e) {
                imagePreview.setImage(null);
                imageStatusLabel.setText("(error al cargar)");
            }
        } else {
            imagePreview.setImage(null);
            imageStatusLabel.setText("(sin imagen)");
        }
    }

    @FXML
    private void onSeleccionarImagen() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Seleccionar imagen del modelo");
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Imágenes",
                        "*.png", "*.jpg", "*.jpeg", "*.gif", "*.webp"),
                new FileChooser.ExtensionFilter("Todos los archivos", "*.*"));
        File file = fc.showOpenDialog(marcaField.getScene().getWindow());
        if (file != null) {
            selectedImageFile = file;
            Image img = new Image(file.toURI().toString(), 200, 150, true, true);
            imagePreview.setImage(img);
            imageStatusLabel.setText(file.getName());
        }
    }

    @FXML
    private void onQuitarImagen() {
        selectedImageFile = null;
        imagePreview.setImage(null);
        imageStatusLabel.setText("(sin imagen)");
    }

    @FXML
    private void onGuardar() {
        String marca = marcaField.getText().trim();
        if (marca.isEmpty()) { mostrarError("La marca es obligatoria"); return; }
        String modelo = modeloField.getText().trim();
        if (modelo.isEmpty()) { mostrarError("El modelo es obligatorio"); return; }
        String anoText = anoField.getText().trim();
        int año;
        try {
            año = Integer.parseInt(anoText);
        } catch (NumberFormatException e) {
            mostrarError("El año debe ser un número"); return;
        }
        String combustible = combustibleCombo.getValue();
        if (combustible == null || combustible.isEmpty()) {
            mostrarError("Selecciona o escribe el combustible"); return;
        }

        ModeloVehiculo m = new ModeloVehiculo();
        m.setMarca(marca);
        m.setModelo(modelo);
        m.setAño(año);
        m.setCombustible(combustible);

        try {
            if (modeloExistente != null) {
                api.updateModelo(modeloExistente.getIdModelo(), m);
            } else {
                ModeloVehiculo created = api.createModelo(m);
                modeloExistente = created;
            }
            if (selectedImageFile != null && modeloExistente != null) {
                api.uploadModeloImage(modeloExistente.getIdModelo(), selectedImageFile.toPath());
            }
            cerrar();
        } catch (Exception e) {
            mostrarError("Error al guardar: " + e.getMessage());
        }
    }

    @FXML
    private void onCancelar() { cerrar(); }

    private void cerrar() { ((Stage) marcaField.getScene().getWindow()).close(); }

    private void mostrarError(String msg) {
        new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK).showAndWait();
    }
}

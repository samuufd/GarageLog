package org.garagelog.desktop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.Pieza;

public class PiezaController {

    @FXML private TableView<Pieza> piezasTable;
    @FXML private TableColumn<Pieza, Integer> colId;
    @FXML private TableColumn<Pieza, String> colNombre;
    @FXML private TableColumn<Pieza, String> colDescripcion;
    @FXML private TableColumn<Pieza, Double> colPrecio;
    @FXML private Button editarBtn;
    @FXML private Button eliminarBtn;

    private final ApiClient api = new ApiClient();
    private final ObservableList<Pieza> piezas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idPieza"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        piezasTable.setItems(piezas);

        var sel = piezasTable.getSelectionModel().selectedItemProperty();
        editarBtn.disableProperty().bind(sel.isNull());
        eliminarBtn.disableProperty().bind(sel.isNull());

        cargar();
    }

    private void cargar() {
        try {
            piezas.setAll(api.getPiezas());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNuevo() { abrirFormulario(null); }

    @FXML
    private void onEditar() {
        Pieza sel = piezasTable.getSelectionModel().getSelectedItem();
        if (sel != null) abrirFormulario(sel);
    }

    @FXML
    private void onEliminar() {
        Pieza sel = piezasTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar pieza " + sel.getNombre() + "?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                api.deletePieza(sel.getIdPieza());
                cargar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirFormulario(Pieza p) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/garagelog/desktop/pieza-form-view.fxml"));
            Scene scene = new Scene(loader.load(), 420, 280);
            PiezaFormController controller = loader.getController();
            if (p != null) controller.setPieza(p);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(piezasTable.getScene().getWindow());
            stage.setTitle(p == null ? "Nueva Pieza" : "Editar Pieza");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/garagelog/desktop/GarageLog_desktop_logo.png")));
            stage.setScene(scene);
            stage.showAndWait();
            cargar();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onCerrar() {
        ((Stage) piezasTable.getScene().getWindow()).close();
    }
}

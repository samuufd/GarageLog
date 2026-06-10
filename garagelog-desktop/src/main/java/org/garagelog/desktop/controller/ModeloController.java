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
import org.garagelog.desktop.model.ModeloVehiculo;

public class ModeloController {

    @FXML private TableView<ModeloVehiculo> modelosTable;
    @FXML private TableColumn<ModeloVehiculo, Integer> colId;
    @FXML private TableColumn<ModeloVehiculo, String> colMarca;
    @FXML private TableColumn<ModeloVehiculo, String> colModelo;
    @FXML private TableColumn<ModeloVehiculo, Integer> colAno;
    @FXML private TableColumn<ModeloVehiculo, String> colCombustible;
    @FXML private Button editarBtn;
    @FXML private Button eliminarBtn;

    private final ApiClient api = new ApiClient();
    private final ObservableList<ModeloVehiculo> modelos = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idModelo"));
        colMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        colModelo.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        colAno.setCellValueFactory(new PropertyValueFactory<>("año"));
        colCombustible.setCellValueFactory(new PropertyValueFactory<>("combustible"));
        modelosTable.setItems(modelos);

        var sel = modelosTable.getSelectionModel().selectedItemProperty();
        editarBtn.disableProperty().bind(sel.isNull());
        eliminarBtn.disableProperty().bind(sel.isNull());

        cargar();
    }

    private void cargar() {
        try {
            modelos.setAll(api.getModelos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNuevo() {
        abrirFormulario(null);
    }

    @FXML
    private void onEditar() {
        ModeloVehiculo sel = modelosTable.getSelectionModel().getSelectedItem();
        if (sel != null) abrirFormulario(sel);
    }

    @FXML
    private void onEliminar() {
        ModeloVehiculo sel = modelosTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar modelo " + sel.getMarca() + " " + sel.getModelo() + "?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                api.deleteModelo(sel.getIdModelo());
                cargar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void abrirFormulario(ModeloVehiculo m) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/garagelog/desktop/modelo-form-view.fxml"));
            Scene scene = new Scene(loader.load(), 420, 515);
            ModeloFormController controller = loader.getController();
            if (m != null) controller.setModelo(m);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(modelosTable.getScene().getWindow());
            stage.setTitle(m == null ? "Nuevo Modelo" : "Editar Modelo");
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
        ((Stage) modelosTable.getScene().getWindow()).close();
    }
}

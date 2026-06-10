package org.garagelog.desktop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.Mantenimiento;
import org.garagelog.desktop.model.MantenimientoPieza;
import org.garagelog.desktop.model.Vehiculo;

public class MantenimientoController {

    @FXML private Label tituloLabel;
    @FXML private TableView<Mantenimiento> mantenimientosTable;
    @FXML private TableColumn<Mantenimiento, Integer> colId;
    @FXML private TableColumn<Mantenimiento, String> colFecha;
    @FXML private TableColumn<Mantenimiento, Integer> colKm;
    @FXML private TableColumn<Mantenimiento, Double> colCoste;
    @FXML private TableColumn<Mantenimiento, String> colObs;

    @FXML private Button eliminarBtn;
    @FXML private TableView<MantenimientoPieza> piezasTable;
    @FXML private TableColumn<MantenimientoPieza, String> colPiezaId;
    @FXML private TableColumn<MantenimientoPieza, Integer> colCantidad;
    @FXML private TableColumn<MantenimientoPieza, Double> colPrecio;
    @FXML private TableColumn<MantenimientoPieza, String> colPiezaObs;

    private final ApiClient api = new ApiClient();
    private final ObservableList<Mantenimiento> mantenimientos = FXCollections.observableArrayList();
    private final ObservableList<MantenimientoPieza> piezas = FXCollections.observableArrayList();

    private Vehiculo vehiculo;

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
        tituloLabel.setText("Mantenimientos de " + vehiculo.getMatricula());
        cargarMantenimientos();
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idMantenimiento"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("km"));
        colCoste.setCellValueFactory(new PropertyValueFactory<>("costeTotal"));
        colObs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        mantenimientosTable.setItems(mantenimientos);

        colPiezaId.setCellValueFactory(new PropertyValueFactory<>("nombrePieza"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPiezaObs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        piezasTable.setItems(piezas);

        eliminarBtn.disableProperty().bind(mantenimientosTable.getSelectionModel().selectedItemProperty().isNull());

        mantenimientosTable.getSelectionModel().selectedItemProperty()
                .addListener((obs, old, sel) -> {
                    if (sel != null) {
                        cargarPiezas(sel);
                    }
                });
    }

    private void cargarMantenimientos() {
        try {
            mantenimientos.setAll(api.getMantenimientosByVehiculo(vehiculo.getIdVehiculo()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarPiezas(Mantenimiento m) {
        try {
            Mantenimiento full = api.getMantenimiento(m.getIdMantenimiento());
            if (full.getPiezas() != null) {
                piezas.setAll(full.getPiezas());
            } else {
                piezas.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onNuevoMantenimiento() {
        if (vehiculo == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/garagelog/desktop/mantenimiento-form-view.fxml"));
            Scene scene = new Scene(loader.load(), 540, 640);
            MantenimientoFormController controller = loader.getController();
            controller.setVehiculo(vehiculo);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(mantenimientosTable.getScene().getWindow());
            stage.setTitle("Nuevo Mantenimiento - " + vehiculo.getMatricula());
            stage.setScene(scene);
            stage.showAndWait();
            cargarMantenimientos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onEliminarMantenimiento() {
        Mantenimiento sel = mantenimientosTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar mantenimiento #" + sel.getIdMantenimiento() + "?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                api.deleteMantenimiento(sel.getIdMantenimiento());
                cargarMantenimientos();
                piezas.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onCerrar() {
        ((Stage) tituloLabel.getScene().getWindow()).close();
    }
}

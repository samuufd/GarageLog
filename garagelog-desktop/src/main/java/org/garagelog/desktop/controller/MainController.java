package org.garagelog.desktop.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.ModeloVehiculo;
import org.garagelog.desktop.model.Vehiculo;

public class MainController {

    @FXML private TableView<Vehiculo> vehiculosTable;
    @FXML private TableColumn<Vehiculo, Integer> colId;
    @FXML private TableColumn<Vehiculo, String> colMatricula;
    @FXML private TableColumn<Vehiculo, String> colModelo;
    @FXML private TableColumn<Vehiculo, Integer> colKm;
    @FXML private TableColumn<Vehiculo, String> colFecha;
    @FXML private Button editarBtn;
    @FXML private Button eliminarBtn;
    @FXML private Button mantenimientosBtn;
    @FXML private VBox detallePanel;
    @FXML private VBox detallePlaceholder;
    @FXML private VBox detalleContenido;
    @FXML private ImageView detalleImagen;
    @FXML private Label detalleMatricula;
    @FXML private Label detalleModelo;
    @FXML private Label detalleKm;
    @FXML private Label detalleFecha;
    @FXML private Label detalleNotas;

    private final ApiClient api = new ApiClient();
    private final ObservableList<Vehiculo> vehiculos = FXCollections.observableArrayList();
    private final Image noImage = new Image(getClass().getResourceAsStream("/org/garagelog/desktop/no-image.jpg"));

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idVehiculo"));
        colMatricula.setCellValueFactory(new PropertyValueFactory<>("matricula"));
        colKm.setCellValueFactory(new PropertyValueFactory<>("kmIniciales"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaCompra"));

        colModelo.setCellValueFactory(cellData -> {
            ModeloVehiculo mv = cellData.getValue().getModeloVehiculo();
            String nombre = (mv != null) ? mv.getMarca() + " " + mv.getModelo() : "";
            return new SimpleStringProperty(nombre);
        });

        vehiculosTable.setItems(vehiculos);

        var sel = vehiculosTable.getSelectionModel().selectedItemProperty();
        editarBtn.disableProperty().bind(sel.isNull());
        eliminarBtn.disableProperty().bind(sel.isNull());
        mantenimientosBtn.disableProperty().bind(sel.isNull());

        sel.addListener((obs, old, v) -> {
            if (v != null) {
                mostrarDetalle(v);
            } else {
                mostrarPlaceholder();
            }
        });
        mostrarPlaceholder();

        cargarVehiculos();
    }

    private void mostrarDetalle(Vehiculo v) {
        detalleMatricula.setText(v.getMatricula());
        ModeloVehiculo mv = v.getModeloVehiculo();
        detalleModelo.setText(mv != null ? mv.getMarca() + " " + mv.getModelo() : "-");
        detalleKm.setText(v.getKmIniciales() + " km");
        detalleFecha.setText(v.getFechaCompra());
        detalleNotas.setText(v.getNotas() != null && !v.getNotas().isBlank() ? v.getNotas() : "—");

        if (mv != null && mv.getRutaImagen() != null && !mv.getRutaImagen().isBlank()) {
            String url = ApiClient.BASE_URL + "/imagenes/" + mv.getRutaImagen();
            Image img = new Image(url, 230, 160, true, true, true);
            img.errorProperty().addListener((o, oldErr, hasErr) -> {
                if (hasErr) detalleImagen.setImage(noImage);
            });
            detalleImagen.setImage(img);
        } else {
            detalleImagen.setImage(noImage);
        }
        detallePlaceholder.setVisible(false);
        detallePlaceholder.setManaged(false);
        detalleContenido.setVisible(true);
        detalleContenido.setManaged(true);
    }

    private void mostrarPlaceholder() {
        detalleImagen.setImage(noImage);
        detalleContenido.setVisible(false);
        detalleContenido.setManaged(false);
        detallePlaceholder.setVisible(true);
        detallePlaceholder.setManaged(true);
    }

    private void cargarVehiculos() {
        try {
            vehiculos.setAll(api.getVehiculos());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirFormVehiculo(Vehiculo v) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/garagelog/desktop/vehiculo-form-view.fxml"));
            Scene scene = new Scene(loader.load(), 420, 320);
            VehiculoFormController controller = loader.getController();
            if (v != null) controller.setVehiculo(v);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(vehiculosTable.getScene().getWindow());
            stage.setTitle(v == null ? "Nuevo Vehículo" : "Editar Vehículo");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/garagelog/desktop/GarageLog_desktop_logo.png")));
            stage.setScene(scene);
            stage.showAndWait();
            cargarVehiculos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void abrirVentana(String fxml, String titulo, double ancho, double alto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load(), ancho, alto);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(vehiculosTable.getScene().getWindow());
            stage.setTitle(titulo);
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/garagelog/desktop/GarageLog_desktop_logo.png")));
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onRefrescar() {
        cargarVehiculos();
    }

    @FXML
    private void onNuevoVehiculo() { abrirFormVehiculo(null); }

    @FXML
    private void onEditarVehiculo() {
        Vehiculo sel = vehiculosTable.getSelectionModel().getSelectedItem();
        if (sel != null) abrirFormVehiculo(sel);
    }

    @FXML
    private void onEliminarVehiculo() {
        Vehiculo sel = vehiculosTable.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "¿Eliminar vehículo " + sel.getMatricula() + "?", ButtonType.YES, ButtonType.NO);
        if (confirm.showAndWait().orElse(ButtonType.NO) == ButtonType.YES) {
            try {
                api.deleteVehiculo(sel.getIdVehiculo());
                cargarVehiculos();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void onVerMantenimientos() {
        Vehiculo selected = vehiculosTable.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/garagelog/desktop/mantenimientos-view.fxml"));
            Scene scene = new Scene(loader.load(), 700, 600);
            MantenimientoController controller = loader.getController();
            controller.setVehiculo(selected);
            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(vehiculosTable.getScene().getWindow());
            stage.setTitle("Mantenimientos - " + selected.getMatricula());
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/garagelog/desktop/GarageLog_desktop_logo.png")));
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onVerModelos() { abrirVentana("/org/garagelog/desktop/modelos-view.fxml", "Gestión de Modelos", 600, 500); }

    @FXML
    private void onVerPiezas() { abrirVentana("/org/garagelog/desktop/piezas-view.fxml", "Gestión de Piezas", 700, 500); }

    @FXML
    private void onAyuda() { abrirVentana("/org/garagelog/desktop/help-view.fxml", "Ayuda", 850, 550); }

    @FXML
    private void onAcercaDe() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.initModality(Modality.WINDOW_MODAL);
        alert.initOwner(vehiculosTable.getScene().getWindow());
        alert.setTitle("Acerca de GarageLog Desktop");
        alert.setHeaderText("GarageLog Desktop v1.0");
        alert.setContentText("Aplicación de escritorio para la gestión de vehículos, mantenimientos, modelos y piezas de un taller mecánico.");
        alert.showAndWait();
    }
}

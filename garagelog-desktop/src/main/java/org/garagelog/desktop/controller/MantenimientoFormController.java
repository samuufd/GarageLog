package org.garagelog.desktop.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.garagelog.desktop.api.ApiClient;
import org.garagelog.desktop.model.*;

import java.util.ArrayList;
import java.util.Locale;

public class MantenimientoFormController {

    @FXML private DatePicker fechaPicker;
    @FXML private TextField kmField;
    @FXML private TextField costeField;
    @FXML private TextField manoObraField;
    @FXML private TextArea observacionesArea;
    @FXML private ComboBox<Pieza> piezaCombo;
    @FXML private TextField cantidadField;
    @FXML private TextField precioField;
    @FXML private TableView<MantenimientoPieza> piezasTable;
    @FXML private TableColumn<MantenimientoPieza, String> colPiezaNombre;
    @FXML private TableColumn<MantenimientoPieza, Integer> colCantidad;
    @FXML private TableColumn<MantenimientoPieza, Double> colPrecio;
    @FXML private TableColumn<MantenimientoPieza, String> colPiezaObs;

    private final ApiClient api = new ApiClient();
    private final ObservableList<MantenimientoPieza> piezasSeleccionadas = FXCollections.observableArrayList();
    private Vehiculo vehiculo;

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    @FXML
    public void initialize() {
        colPiezaNombre.setCellValueFactory(new PropertyValueFactory<>("nombrePieza"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioUnitario"));
        colPiezaObs.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        piezasTable.setItems(piezasSeleccionadas);

        cargarPiezas();

        manoObraField.textProperty().addListener((obs, old, val) -> calcularTotal());

        piezaCombo.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                precioField.setText(String.valueOf(sel.getPrecio()));
            }
        });
    }

    private void cargarPiezas() {
        try {
            piezaCombo.setItems(FXCollections.observableArrayList(api.getPiezas()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onAgregarPieza() {
        Pieza pieza = piezaCombo.getValue();
        if (pieza == null) {
            mostrarError("Selecciona una pieza");
            return;
        }
        String cantText = cantidadField.getText().trim();
        if (cantText.isEmpty()) {
            mostrarError("Indica la cantidad");
            return;
        }
        int cantidad;
        try {
            cantidad = Integer.parseInt(cantText);
        } catch (NumberFormatException e) {
            mostrarError("Cantidad debe ser un número");
            return;
        }
        String precioText = precioField.getText().trim();
        double precioUnitario;
        try {
            precioUnitario = Double.parseDouble(precioText);
        } catch (NumberFormatException e) {
            mostrarError("Precio debe ser un número");
            return;
        }

        MantenimientoPieza mp = new MantenimientoPieza();
        mp.setPieza(pieza);
        mp.setCantidad(cantidad);
        mp.setPrecioUnitario(precioUnitario);

        piezasSeleccionadas.add(mp);
        calcularTotal();

        piezaCombo.setValue(null);
        cantidadField.clear();
        precioField.clear();
    }

    @FXML
    private void onQuitarPieza() {
        MantenimientoPieza sel = piezasTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            piezasSeleccionadas.remove(sel);
            calcularTotal();
        }
    }

    private void calcularTotal() {
        double total = 0;
        for (MantenimientoPieza mp : piezasSeleccionadas) {
            total += mp.getCantidad() * mp.getPrecioUnitario();
        }
        try {
            total += Double.parseDouble(manoObraField.getText().trim());
        } catch (NumberFormatException e) {
            // ignore
        }
        costeField.setText(String.format(Locale.ROOT, "%.2f", total));
    }

    @FXML
    private void onGuardar() {
        if (fechaPicker.getValue() == null) {
            mostrarError("Selecciona una fecha");
            return;
        }
        String kmText = kmField.getText().trim();
        if (kmText.isEmpty()) {
            mostrarError("Los KM son obligatorios");
            return;
        }
        int km;
        try {
            km = Integer.parseInt(kmText);
        } catch (NumberFormatException e) {
            mostrarError("KM debe ser un número");
            return;
        }
        MantenimientoRequest req = new MantenimientoRequest();
        req.setIdVehiculo(vehiculo.getIdVehiculo());
        req.setFecha(fechaPicker.getValue().toString());
        req.setKm(km);
        double costeTotal = 0;
        for (MantenimientoPieza mp : piezasSeleccionadas) {
            costeTotal += mp.getCantidad() * mp.getPrecioUnitario();
        }
        double manoObra = 0;
        try {
            manoObra = Double.parseDouble(manoObraField.getText().trim());
        } catch (NumberFormatException e) {
            // ignore
        }
        costeTotal += manoObra;
        req.setCosteTotal(costeTotal);
        req.setManoObra(manoObra);
        req.setObservaciones(observacionesArea.getText().trim());

        req.setPiezas(new ArrayList<>());
        for (MantenimientoPieza mp : piezasSeleccionadas) {
            MantenimientoRequest.PiezaRequest pr = new MantenimientoRequest.PiezaRequest();
            pr.setIdPieza(mp.getIdPieza());
            pr.setCantidad(mp.getCantidad());
            pr.setPrecioUnitario(mp.getPrecioUnitario());
            req.getPiezas().add(pr);
        }

        try {
            api.createMantenimiento(req);
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
        ((Stage) fechaPicker.getScene().getWindow()).close();
    }

    private void mostrarError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK);
        alert.showAndWait();
    }
}

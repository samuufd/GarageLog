package org.garagelog.desktop.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class HelpController {

    @FXML private TreeView<String> navTree;
    @FXML private WebView contentView;

    private String htmlContent;
    private final Map<String, String> sectionMap = new HashMap<>();

    @FXML
    public void initialize() {
        htmlContent = loadHtml();

        TreeItem<String> root = new TreeItem<>("Ayuda");
        root.setExpanded(true);

        TreeItem<String> intro = createItem("Introducción", "introduccion");
        TreeItem<String> vehiculos = createSection("Vehículos", new String[][]{
            {"Listar vehículos", "vehiculos"},
            {"Crear vehículo", "vehiculos-crear"},
            {"Editar vehículo", "vehiculos-editar"},
            {"Eliminar vehículo", "vehiculos-eliminar"}
        });
        TreeItem<String> modelos = createSection("Modelos", new String[][]{
            {"Listar modelos", "modelos"},
            {"Crear modelo", "modelos-crear"},
            {"Editar modelo", "modelos-editar"},
            {"Eliminar modelo", "modelos-eliminar"}
        });
        TreeItem<String> piezas = createSection("Piezas", new String[][]{
            {"Listar piezas", "piezas"},
            {"Crear pieza", "piezas-crear"},
            {"Editar pieza", "piezas-editar"},
            {"Eliminar pieza", "piezas-eliminar"}
        });
        TreeItem<String> mantenimientos = createSection("Mantenimientos", new String[][]{
            {"Listar mantenimientos", "mantenimientos"},
            {"Crear mantenimiento", "mantenimientos-crear"},
            {"Eliminar mantenimiento", "mantenimientos-eliminar"}
        });
        TreeItem<String> consejos = createItem("Consejos de uso", "consejos");
        TreeItem<String> errores = createItem("Errores comunes", "errores");

        root.getChildren().addAll(intro, vehiculos, modelos, piezas, mantenimientos, consejos, errores);
        navTree.setRoot(root);
        navTree.setShowRoot(true);

        navTree.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null && sel.getValue() != null) {
                String sectionId = sectionMap.get(sel.getValue());
                if (sectionId != null) {
                    navigateTo(sectionId);
                }
            }
        });

        navTree.getSelectionModel().select(intro);
    }

    private TreeItem<String> createItem(String label, String sectionId) {
        TreeItem<String> item = new TreeItem<>(label);
        sectionMap.put(label, sectionId);
        return item;
    }

    private TreeItem<String> createSection(String label, String[][] children) {
        TreeItem<String> section = new TreeItem<>(label);
        section.setExpanded(false);
        for (String[] child : children) {
            TreeItem<String> item = new TreeItem<>(child[0]);
            sectionMap.put(child[0], child[1]);
            section.getChildren().add(item);
        }
        return section;
    }

    private void navigateTo(String sectionId) {
        String html = htmlContent.replace(
            "class=\"section active\"",
            "class=\"section\""
        ).replace(
            "id=\"" + sectionId + "\" class=\"section\"",
            "id=\"" + sectionId + "\" class=\"section active\""
        );
        contentView.getEngine().loadContent(html);
    }

    private String loadHtml() {
        try (var is = getClass().getResourceAsStream("/org/garagelog/desktop/help-content.html")) {
            if (is != null) {
                return new String(is.readAllBytes(), "UTF-8");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "<html><body><h1>Error al cargar la ayuda</h1></body></html>";
    }

    @FXML
    private void onCerrar() {
        ((Stage) navTree.getScene().getWindow()).close();
    }
}
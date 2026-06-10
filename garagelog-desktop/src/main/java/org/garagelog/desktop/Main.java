package org.garagelog.desktop;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/org/garagelog/desktop/main-view.fxml"));
        Scene scene = new Scene(fxml.load(), 950, 640);
        stage.setTitle("GarageLog Desktop");
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/org/garagelog/desktop/GarageLog_desktop_logo.png")));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

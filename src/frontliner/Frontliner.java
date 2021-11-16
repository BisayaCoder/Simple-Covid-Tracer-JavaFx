/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frontliner;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Administrator
 */
public class Frontliner extends Application {

    Parent root;
    @SuppressWarnings("all")
    @Override
    public void start(Stage stage) throws Exception {
        root = FXMLLoader.load(getClass().getResource("/Interface/Dashboard.fxml"));
        Scene scene = new Scene(root);
        stage.setOnCloseRequest(e -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                    "Do you really want to close this application?", ButtonType.YES, ButtonType.NO);
            ButtonType result = alert.showAndWait().orElse(ButtonType.NO);
            if (ButtonType.NO.equals(result)) {
                e.consume();
            } else {
                com.sun.javafx.application.PlatformImpl.tkExit();
                Platform.exit();
            }
        });
        stage.setScene(scene);
        stage.setResizable(false);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/Backgrounds/icon.png")));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}

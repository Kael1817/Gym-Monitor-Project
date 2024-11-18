package com.gymmonitor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    private static Stage stg;
    @Override
    public void start(Stage firststage) throws IOException {
        stg = firststage;
        firststage.setResizable(true);
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        firststage.setTitle("GYMONITOR");
        firststage.setScene(new Scene(root));
        firststage.sizeToScene();
        firststage.show();
        firststage.centerOnScreen();
    }
    public void changeScene(String fxml) throws IOException{
        Parent pane = FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
        stg.sizeToScene();
    }

    public static void main(String[] args) {
        launch();
    }
}
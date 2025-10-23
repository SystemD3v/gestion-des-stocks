package com.example.javafx.view;

import com.example.javafx.Controller.APICall;
import com.example.javafx.model.Model;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class Commandes extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Commandes.class.getResource("Commandes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 720);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }
}

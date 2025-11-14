package com.example.javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class Launcher extends Application {

    private BorderPane root;
    private static Launcher instance;


    @Override
    public void start(Stage primaryStage) {
        instance = this;
        primaryStage.setTitle("VinStock");

        try {
            root = new BorderPane();
            Scene scene = new Scene(root, 1280, 720);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("/com/example/javafx/view/style.css")).toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

            switchScreen(Screen.HOME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void switchScreen(Screen screen) {
        try {
            var url = getClass().getResource(screen.getFxmlPath());
            if (url == null) {
                throw new IllegalStateException("FXML introuvable: " + screen.getFxmlPath());
            }
            FXMLLoader loader = new FXMLLoader(url);
            Parent view = loader.load();
            root.setCenter(view);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Launcher getInstance() {
        return instance;
    }

    public enum Screen {
        HOME("/com/example/javafx/view/Stock.fxml"),
        FOURNISSUER("/com/example/javafx/view/Fournisseur.fxml"),
        COMMANDE("/com/example/javafx/view/Commandes.fxml");

        private final String fxmlPath;

        Screen(String fxmlPath) {
            this.fxmlPath = fxmlPath;
        }

        public String getFxmlPath() {
            return fxmlPath;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
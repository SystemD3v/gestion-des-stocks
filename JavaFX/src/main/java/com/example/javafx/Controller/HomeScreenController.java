// Example implementation in a controller class
package com.example.javafx.Controller;

import com.example.javafx.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class HomeScreenController {
    @FXML
    public AnchorPane sidebar;
    @FXML
    private Pane homeScreenPane;

    public void initialize() {
    }

    @FXML
    private void goToCommande() {
        System.out.println("going to commande");
        Launcher.getInstance().switchScreen(Launcher.Screen.COMMANDE);
    }
    @FXML
    private void goToDashFournisseur() {
        Launcher.getInstance().switchScreen(Launcher.Screen.FOURNISSUER);
    }

    @FXML
    private void goToDashBoard() {
        Launcher.getInstance().switchScreen(Launcher.Screen.HOME);
    }


}
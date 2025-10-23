package com.example.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import netscape.javascript.JSObject;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        int ouais = 5;
        ObservableList<PieChart.Data> data = FXCollections.observableArrayList(
                new PieChart.Data("En stock", ouais),
                new PieChart.Data("Réservé", 25),
                new PieChart.Data("Rupture", 15)
        );


    }
}

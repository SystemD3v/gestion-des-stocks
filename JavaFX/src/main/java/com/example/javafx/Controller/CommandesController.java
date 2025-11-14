package com.example.javafx.Controller;

import com.example.javafx.model.Model.Instance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CommandesController {

    @FXML
    private TableView<Instance> tableCommandes;

    @FXML
    private TableColumn<Instance, Integer> idColumn;

    @FXML
    private TableColumn<Instance, String> timestampColumn;

    @FXML
    private TableColumn<Instance, Integer> stockidColumn;

    @FXML
    private TableColumn<Instance, Integer> supplieridColumn;

    @FXML
    private TableColumn<Instance, Boolean> completeColumn;

    @FXML
    public void initialize() throws Exception {

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));
        stockidColumn.setCellValueFactory(new PropertyValueFactory<>("stockId"));
        supplieridColumn.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        completeColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));

        List<Instance> instancesFromApi = APICall.retrieveInstance("get_handler");

        ObservableList<Instance> data = FXCollections.observableArrayList(instancesFromApi);

        tableCommandes.setItems(data);
    }
}
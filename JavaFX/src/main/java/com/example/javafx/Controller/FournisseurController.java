package com.example.javafx.Controller;

import com.example.javafx.model.Model;
import com.example.javafx.model.Model.supplier;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.SneakyThrows;

import java.util.List;

public class FournisseurController {


    @FXML
    private TextField deleteId;
    @FXML
    private TextField insertId;
    @FXML
    private TextField insertName;
    @FXML
    private TextField insertPhone;
    @FXML
    private TextField insertAddress;
    @FXML
    private TextField modifyId;
    @FXML
    private TextField modifyName;
    @FXML
    private TextField modifyPhone;
    @FXML
    private TextField modifyAddress;
    @FXML
    private Button deleteButton;
    @FXML
    private Button insertButton;
    @FXML
    private Button modifyButton;
    @FXML
    private TableView<supplier> tablefurnisseur;
    @FXML
    private TableColumn<supplier, Integer> idColumn;
    @FXML
    private TableColumn<supplier, String> nomColumn;
    @FXML
    private TableColumn<supplier, Integer> telColumn;
    @FXML
    private TableColumn<supplier, String> addresColumn;

    @SneakyThrows
    @FXML
    public void initialize() throws Exception {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));
        telColumn.setCellValueFactory(new PropertyValueFactory<>("supplier_phone"));
        addresColumn.setCellValueFactory(new PropertyValueFactory<>("supplier_address"));

        loadData();

        deleteButton.setOnAction(this::handleDeleteButtonAction);
        modifyButton.setOnAction(this::handleModifyButtonAtion);
    }

    @FXML
    private void handleModifyButtonAtion(ActionEvent actionEvent) {
        try {
            String id = modifyId.getText();
            String name = modifyName.getText();
            String phone = modifyPhone.getText();
            String address = modifyAddress.getText();
            APICall.editSupplier("edit_supplier/",id,name,phone,address);
            loadData();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        try {
            List<supplier> supplierFromApi = APICall.retrieveSupplier("get_supplier");
            ObservableList<supplier> data = FXCollections.observableArrayList(supplierFromApi);
            tablefurnisseur.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButtonAction(javafx.event.ActionEvent actionEvent) {
        try {
            String id = deleteId.getText();
            APICall.delete("sup_supplier/",id);
            System.out.println("sup_supplier/"+id);
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}

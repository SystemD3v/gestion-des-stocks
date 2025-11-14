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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class FournisseurController {


    @FXML
    private TextField deleteId;
    @FXML
    private TextField insertId;
    @FXML
    private Button Select;
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

        //setting the button to have a action when pressed and liking them to the correct function
        deleteButton.setOnAction(this::handleDeleteButtonAction);
        modifyButton.setOnAction(this::handleModifyButtonAction);
        insertButton.setOnAction(this::handleInsertButtonAction);
        Select.setOnAction(this::handleSelectButtonAction);
    }

    @FXML
    private void handleSelectButtonAction(ActionEvent actionEvent) { //to display the info of selected supplier
        try {
            // Get the ID from the TextField
            String id = modifyId.getText();

            // Call your API to retrieve the supplier by ID
            List<supplier> suppliers = APICall.retrieveSupplierById("get_supplier/", id);

            if (!suppliers.isEmpty()) {
                // Assuming ID is unique, take the first supplier
                supplier selectedSupplier = suppliers.get(0);

                // Store the supplier info in variables
                String supplierName = selectedSupplier.getSupplier_name();
                String supplierPhone = String.valueOf(selectedSupplier.getSupplier_phone());
                String supplierAddress = selectedSupplier.getSupplier_address();

                // Optionally populate the modify fields
                modifyName.setText(supplierName);
                modifyPhone.setText(supplierPhone);
                modifyAddress.setText(supplierAddress);

            } else {
                System.out.println("No supplier found with ID: " + id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleInsertButtonAction(ActionEvent actionEvent) {
        try {
            String name = insertName.getText();
            String phone = insertPhone.getText();
            String address = insertAddress.getText();
            APICall.createSupplier("create_supplier",name,phone,address);

            loadData();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleModifyButtonAction(ActionEvent actionEvent) { // button to modify the supplier
        try {
            String id = modifyId.getText();
            String name = URLEncoder.encode(modifyName.getText(), StandardCharsets.UTF_8.toString()).replace("+","%20");
            String phone = URLEncoder.encode(modifyPhone.getText(), StandardCharsets.UTF_8.toString()).replace("+","%20");
            String address = URLEncoder.encode(modifyAddress.getText(), StandardCharsets.UTF_8.toString()).replace("+","%20");
            APICall.editSupplier("edit_supplier/",id,name,phone,address);
            loadData();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() { //loads the data out of the initialize to make sure we can load it when ever a change is made to the data
        try {
            List<supplier> supplierFromApi = APICall.retrieveSupplier("get_supplier");
            ObservableList<supplier> data = FXCollections.observableArrayList(supplierFromApi);
            tablefurnisseur.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteButtonAction(javafx.event.ActionEvent actionEvent) { // button to delete supplier
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

package com.example.javafx.Controller;

import com.example.javafx.model.Model;
import com.example.javafx.model.Model.Instance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandesController {

    @FXML private TableView<Instance> tableCommandes;
    @FXML private TableColumn<Instance, Integer> idColumn;
    @FXML private TableColumn<Instance, String> timestampColumn;
    @FXML private TableColumn<Instance, String> stockidColumn;
    @FXML private TableColumn<Instance, String> supplieridColumn;
    @FXML private TableColumn<Instance, String> completeColumn;

    @FXML private TableView<Instance> tableCommandes2;
    @FXML private TableColumn<Instance, Integer> idColumn2;
    @FXML private TableColumn<Instance, String> timestampColumn2;
    @FXML private TableColumn<Instance, String> stockidColumn2;
    @FXML private TableColumn<Instance, String> supplieridColumn2;
    @FXML private TableColumn<Instance, String> completeColumn2;

    @FXML private BorderPane mainBorderPane;
    @FXML private AnchorPane centerPane;
    @FXML private Button createOrderButton;

    public void refreshTables(){
        tableCommandes.refresh();
        tableCommandes2.refresh();
    }

    private void modifyStock(String str, Integer amount ) throws Exception {

        List<Model.stock> stocks = APICall.retrieveStock("get_stockById/" + str);
        if (stocks != null && !stocks.isEmpty() && stocks.get(0).label != null) {
            APICall.updateOrder("updateStock", Integer.valueOf(str),"available_quantity", String.valueOf((stocks.get(0).available_quantity - amount)));
        }
    }

    private void addNames(List<Instance> iList) throws Exception {
        for(Instance i : iList){
            if (i.stockId != null) {
                List<Model.stock> stocks = APICall.retrieveStock("get_stockById/" + i.stockId);

                if (stocks != null && !stocks.isEmpty() && stocks.get(0).label != null) {
                    i.stockName = stocks.get(0).label;
                } else {
                    i.stockName = "Unknown";
                }
            }

            if (i.supplierId != null) {
                List<Model.supplier> suppliers = APICall.retrieveSupplier("get_supplier/" + i.supplierId);

                if (suppliers != null && !suppliers.isEmpty() && suppliers.get(0).supplier_name != null) {
                    i.supplierName = suppliers.get(0).supplier_name;
                } else {
                    i.supplierName = "Unknown";
                }
            }

        }

    }

    private List<Instance> dataTable1(List<Instance> iList) {
        List<Instance> filtered = new ArrayList<>();

        for (Instance i : iList) {
            if (!"true".equals(i.completed)) {
                filtered.add(i); // on DUPLIQUE la référence, mais on ne touche pas la liste originale
            }
        }

        return filtered;
    }

    @FXML
    public void initialize() throws Exception {

        // --- Colonnes ---
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        timestampColumn.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));
        stockidColumn.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        supplieridColumn.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        completeColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));

        idColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        timestampColumn2.setCellValueFactory(new PropertyValueFactory<>("orderTimestamp"));
        stockidColumn2.setCellValueFactory(new PropertyValueFactory<>("stockName"));
        supplieridColumn2.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        completeColumn2.setCellValueFactory(new PropertyValueFactory<>("completed"));

        // --- Données depuis l'API ---
        List<Instance> instancesFromApi = APICall.retrieveInstance("get_handler");
        addNames(instancesFromApi);
        List<Instance> listForTable1 = dataTable1(instancesFromApi);
        ObservableList<Instance> data2 = FXCollections.observableArrayList(listForTable1);
        ObservableList<Instance> data = FXCollections.observableArrayList(instancesFromApi);

        tableCommandes.setItems(data2);
        tableCommandes2.setItems(data);

        // --- Ajout du menu clic droit ---
        setupContextMenu();

        // --- Bouton création commande ---
        createOrderButton.setOnAction(event -> openCreateOrderPage());
    }

    // -----------------------------
    //  PAGE CREATION COMMANDE
    // -----------------------------
    private void openCreateOrderPage() {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/com/example/javafx/view/createOrder.fxml")
            );

            BorderPane createOrderPane = loader.load();

            // Donner accès au mainBorderPane
            CreateOrderController coc = loader.getController();
            coc.setMainBorderPane(mainBorderPane, centerPane);

            // Afficher la page correctement
            mainBorderPane.setCenter(createOrderPane.getCenter());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -----------------------------
    //     CONTEXT MENU (clic droit)
    // -----------------------------
    private void setupContextMenu() {

        ContextMenu menu = new ContextMenu();

        MenuItem editStatus = new MenuItem("Modifier le status");
        MenuItem deleteOrder = new MenuItem("Supprimer");

        menu.getItems().addAll(editStatus, deleteOrder);

        tableCommandes.setRowFactory(tv -> {
            TableRow<Instance> row = new TableRow<>();

            row.setOnContextMenuRequested(event -> {
                if (!row.isEmpty()) {
                    tableCommandes.getSelectionModel().select(row.getItem());
                    menu.show(row, event.getScreenX(), event.getScreenY());
                }
            });

            return row;
        });

        editStatus.setOnAction(e -> {
            try {
                modifyStatus();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        deleteOrder.setOnAction(e -> {
            try {
                deleteOrder();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    // -----------------------------
    //     ACTION : MODIFIER STATUS
    // -----------------------------
    private void modifyStatus() throws Exception {
        Instance selected = tableCommandes.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Modifier le status");
        alert.setHeaderText("Commande #" + selected.id);
        alert.setContentText("Marquer comme complétée ?");

        if (alert.showAndWait().get() == ButtonType.OK) {

            selected.completed = String.valueOf(true);

            // Appel API :
            APICall.updateOrder("updateInstance", selected.getId(),"completed","true");
            modifyStock(selected.stockId, selected.requestAmount);

            tableCommandes.refresh();
            tableCommandes2.refresh();
        }
    }

    // -----------------------------
    //     ACTION : SUPPRIMER
    // -----------------------------
    private void deleteOrder() throws Exception {
        Instance selected = tableCommandes.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Suppression");
        alert.setHeaderText("Supprimer la commande #" + selected.id + " ?");
        alert.setContentText("Cette action est irréversible.");

        if (alert.showAndWait().get() == ButtonType.OK) {

            System.out.println(APICall.delete("deleteInstance/",selected.getId()));

            tableCommandes.getItems().remove(selected);
            tableCommandes2.getItems().remove(selected);
        }
    }
}
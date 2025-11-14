package com.example.javafx.Controller;

import com.example.javafx.model.Model;
import com.example.javafx.model.Model.Instance;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.List;

public class CreateOrderController {

    @FXML
    private ComboBox<Model.stock> productComboBox;

    @FXML
    private TextField amountField;

    @FXML
    private ComboBox<Model.user> clientComboBox;

    @FXML
    private Button addProductButton;

    @FXML
    private Button finalizeButton;

    @FXML
    private Button backButton;

    @FXML
    private ListView<String> addedProductsListView;

    private BorderPane mainBorderPane;
    private AnchorPane originalCenterPane;

    private List<Instance> instanceList = new ArrayList<>();

    public void setMainBorderPane(BorderPane mainBorderPane, AnchorPane originalCenterPane) {
        this.mainBorderPane = mainBorderPane;
        this.originalCenterPane = originalCenterPane;
    }

    @FXML
    public void initialize() {

        // Configuration du converter pour la ComboBox des produits
        productComboBox.setConverter(new StringConverter<Model.stock>() {
            @Override
            public String toString(Model.stock stock) {
                if (stock == null) return "";

                // Essayez différentes méthodes possibles
                try {
                    // Essayez getLabel()
                    if (stock.getLabel() != null) {
                        if(stock.getYears() != 0){
                            return stock.getLabel() + " (" + stock.getYears() + ")";
                        }else{
                            return stock.getLabel();
                        }

                    }
                } catch (Exception e) {
                    System.out.println("getLabel() non disponible");
                }

                // Si getLabel() ne fonctionne pas, essayez getName() ou toString()
                try {
                    return stock.toString();
                } catch (Exception e) {
                    return "Produit ID: " + stock.getId();
                }
            }

            @Override
            public Model.stock fromString(String string) {
                return null;
            }
        });

        // Configuration du converter pour la ComboBox des clients
        clientComboBox.setConverter(new StringConverter<Model.user>() {
            @Override
            public String toString(Model.user user) {
                if (user == null) return "";

                // Gestion des valeurs nulles et essai de différentes propriétés
                String firstname = "";
                String lastname = "";

                try {
                    firstname = user.getFirstname() != null ? user.getFirstname() : "";
                    lastname = user.getLastname() != null ? user.getLastname() : "";
                } catch (Exception e) {
                    System.out.println("Erreur getFirstname/getLastname: " + e.getMessage());
                }

                // Si les deux sont vides, essayez d'autres propriétés
                if (firstname.isEmpty() && lastname.isEmpty()) {
                    try {
                        return user.toString();
                    } catch (Exception e) {
                        return "Client ID: " + user.getId();
                    }
                }

                return (firstname + " " + lastname).trim();
            }

            @Override
            public Model.user fromString(String string) {
                return null;
            }
        });

        // Remplissage des combobox avec les données du back
        try {
            List<Model.stock> products = APICall.retrieveStock("get_stock");
            System.out.println("Produits chargés: " + products.size());
            if (!products.isEmpty()) {
                System.out.println("Premier produit: " + products.get(0));
            }
            productComboBox.setItems(FXCollections.observableArrayList(products));

            List<Model.user> clients = new APICall().retrieveUser("get_users");
            System.out.println("Clients chargés: " + clients.size());
            if (!clients.isEmpty()) {
                System.out.println("Premier client: " + clients.get(0));
            }
            clientComboBox.setItems(FXCollections.observableArrayList(clients));
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors du chargement des données : " + e.getMessage());
            alert.showAndWait();
        }

        // Ajouter produit à la liste
        addProductButton.setOnAction(event -> {
            Model.stock selectedProduct = productComboBox.getValue();
            Model.user selectedClient = clientComboBox.getValue();
            String amountText = amountField.getText();

            if (selectedProduct == null || selectedClient == null || amountText.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez remplir tous les champs !");
                alert.showAndWait();
                return;
            }

            int amount;
            try {
                amount = Integer.parseInt(amountText);
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Montant invalide !");
                alert.showAndWait();
                return;
            }

            // Créer instance avec uniquement les IDs
            Instance inst = new Instance();
            inst.stockId = String.valueOf(selectedProduct.getId());
            inst.userId = selectedClient.getId();
            inst.requestAmount = amount;

            instanceList.add(inst);

            // Affichage dans la ListView
            addedProductsListView.getItems().add(
                    selectedProduct.getLabel() + " - " + amount + " - " + selectedClient.getFirstname() + " " + selectedClient.getLastname()
            );

            // Réinitialiser le formulaire
            productComboBox.getSelectionModel().clearSelection();
            clientComboBox.getSelectionModel().clearSelection();
            amountField.clear();
        });

        // Finaliser la commande
        finalizeButton.setOnAction(event -> {
            if (instanceList.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Aucun produit ajouté !");
                alert.showAndWait();
                return;
            }

            try {
                new APICall().createInstance(instanceList);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Commande créée avec succès !");
                alert.showAndWait();

                instanceList.clear();
                addedProductsListView.getItems().clear();

            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Erreur lors de la création de la commande : " + e.getMessage());
                alert.showAndWait();
            }
        });

        // Retour vers commandes
        backButton.setOnAction(event -> {
            if (mainBorderPane != null && originalCenterPane != null) {
                mainBorderPane.setCenter(originalCenterPane);
            }
        });
    }
}
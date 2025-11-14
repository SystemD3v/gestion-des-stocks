package com.example.javafx.Controller;

import com.example.javafx.model.Model.supplier;
import com.example.javafx.model.Model.stock;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Contrôleur pour la gestion de l'inventaire des stocks (vins)
 */
public class StockController {

    // === Composants FXML de la table ===
    @FXML private TableView<stock> tableStocks;
    @FXML private TableColumn<stock, Integer> idColumn;
    @FXML private TableColumn<stock, String> nameColumn;
    @FXML private TableColumn<stock, String> categoryColumn;
    @FXML private TableColumn<stock, Integer> yearColumn;
    @FXML private TableColumn<stock, Integer> quantityColumn;
    @FXML private TableColumn<stock, Double> priceColumn;
    @FXML private TableColumn<stock, Double> totalValueColumn;
    @FXML private TableColumn<stock, String> supplierColumn;

    public String supplierName;

    // === Composants de filtrage ===
    @FXML private TextField searchField;
    @FXML private ComboBox<String> categoryFilter;
    @FXML private ComboBox<String> stockStatusFilter;
    @FXML private Label totalBottlesLabel;

    // Données complètes et filtrées
    private final ObservableList<stock> productList = FXCollections.observableArrayList();
    private final ObservableList<stock> filteredList = FXCollections.observableArrayList();

    private final Map<Integer, String> supplierMap = new HashMap<>();

    /**
     * Récupère les noms des fournisseurs pour chaque stock via l'API
     */
    public void getSupplierName(List<stock> stocks) throws Exception {
        for(stock stock : stocks) {
            // Check if supplier_id exists
            try {
                // Appel API pour récupérer le fournisseur
                List<supplier> suppliers = APICall.retrieveSupplier("get_supplier/" + stock.supplier_id);

                // Check if the list is not empty and the first element is not null
                if (suppliers != null && !suppliers.isEmpty() && suppliers.get(0) != null) {
                    stock.supplier_name = suppliers.get(0).supplier_name;
                } else {
                    // Supplier not found or returned null
                    stock.supplier_name = "Fournisseur introuvable";
                    System.err.println("Supplier not found for ID: " + stock.supplier_id);
                }
            } catch (Exception e) {
                // Handle API errors gracefully
                stock.supplier_name = "Erreur";
                System.err.println("Error fetching supplier for ID " + stock.supplier_id + ": " + e.getMessage());
            }
        }
    }

    /**
     * Initialisation du contrôleur au chargement de la vue
     */
    @FXML
    public void initialize() throws Exception {

        // Liaison des colonnes avec les propriétés du modèle
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("label"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("years"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("available_quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier_name"));

        // Calcul dynamique de la valeur totale (prix × quantité)
        totalValueColumn.setCellValueFactory(cellData -> {
            stock s = cellData.getValue();
            double total = s.getPrice() * s.getAvailable_quantity();
            return new SimpleDoubleProperty(total).asObject();
        });

        tableStocks.setItems(filteredList);

        // Configuration du menu contextuel (clic droit)
        setupContextMenu();

        // Configuration des filtres et listeners
        setupFilters();
        setupSearchListener();

        // Chargement initial des données
        List<stock> stockFromApi = APICall.retrieveStock("get_stock");
        getSupplierName(stockFromApi);
        productList.clear();
        productList.addAll(stockFromApi);

        applyFilters();
        totalStock();
    }

    /**
     * Configure le menu contextuel (clic droit) sur la table
     */
    private void setupContextMenu() {
        ContextMenu contextMenu = new ContextMenu();

        MenuItem deleteItem = new MenuItem("Supprimer du stock");
        deleteItem.setOnAction(event -> handleDeleteStock());

        contextMenu.getItems().add(deleteItem);
        tableStocks.setContextMenu(contextMenu);
    }

    /**
     * Gère la suppression d'une quantité de stock (ou suppression complète)
     */
    private void handleDeleteStock() {
        stock selectedStock = tableStocks.getSelectionModel().getSelectedItem();

        if (selectedStock == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un produit à supprimer");
            alert.showAndWait();
            return;
        }

        // Dialogue pour choisir la quantité à supprimer
        TextInputDialog dialog = new TextInputDialog(String.valueOf(selectedStock.getAvailable_quantity()));
        dialog.setTitle("Supprimer du stock");
        dialog.setHeaderText("Produit : " + selectedStock.getLabel());
        dialog.setContentText("Quantité à supprimer (disponible : " + selectedStock.getAvailable_quantity() + ") :");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(quantityStr -> {
            try {
                int quantityToRemove = Integer.parseInt(quantityStr);

                // Validation de la quantité
                if (quantityToRemove <= 0) {
                    showError("La quantité doit être supérieure à 0");
                    return;
                }

                if (quantityToRemove > selectedStock.getAvailable_quantity()) {
                    showError("La quantité à supprimer ne peut pas dépasser le stock disponible (" +
                            selectedStock.getAvailable_quantity() + ")");
                    return;
                }

                // Calcul de la nouvelle quantité
                int newQuantity = selectedStock.getAvailable_quantity() - quantityToRemove;
                boolean willDeleteCompletely = (newQuantity == 0);

                // Confirmation de suppression
                Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
                confirmation.setTitle("Confirmation");

                if (willDeleteCompletely) {
                    confirmation.setHeaderText("Supprimer complètement ce produit ?");
                    confirmation.setContentText("Produit : " + selectedStock.getLabel() +
                            "\nQuantité actuelle : " + selectedStock.getAvailable_quantity() +
                            "\n\nLe produit sera supprimé de la base de données !");
                } else {
                    confirmation.setHeaderText("Supprimer " + quantityToRemove + " unité(s) ?");
                    confirmation.setContentText("Produit : " + selectedStock.getLabel() +
                            "\nStock actuel : " + selectedStock.getAvailable_quantity() +
                            "\nNouveau stock : " + newQuantity);
                }

                Optional<ButtonType> confirmResult = confirmation.showAndWait();

                if (confirmResult.isPresent() && confirmResult.get() == ButtonType.OK) {
                    boolean success;

                    if (willDeleteCompletely) {
                        // Suppression complète du produit
                        success = APICall.deleteStock(selectedStock.getId());
                    } else {
                        // Mise à jour de la quantité
                        success = APICall.updateStockQuantity(selectedStock.getId(), newQuantity);
                    }

                    if (success) {
                        // Rafraîchissement des données
                        refreshData();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Succès");
                        alert.setHeaderText(null);
                        alert.setContentText(willDeleteCompletely ?
                                "Produit supprimé avec succès !" :
                                "Stock mis à jour avec succès !");
                        alert.showAndWait();
                    } else {
                        showError("Erreur lors de la " + (willDeleteCompletely ? "suppression" : "mise à jour") + " du stock");
                    }
                }

            } catch (NumberFormatException e) {
                showError("Veuillez entrer un nombre valide");
            } catch (Exception e) {
                showError("Erreur : " + e.getMessage());
                e.printStackTrace();
            }
        });
    }

    /**
     * Rafraîchit les données de la table depuis l'API
     */
    private void refreshData() {
        try {
            List<stock> updatedStock = APICall.retrieveStock("get_stock");
            getSupplierName(updatedStock);
            productList.clear();
            productList.addAll(updatedStock);
            applyFilters();
            totalStock();
        } catch (Exception e) {
            showError("Erreur lors du rafraîchissement : " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Affiche un message d'erreur
     */
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Configure les ComboBox de filtrage avec leurs valeurs et listeners
     */
    private void setupFilters() {
        // Filtre par catégorie de vin
        categoryFilter.setItems(FXCollections.observableArrayList(
                "Toutes", "Rouge", "Blanc", "Pétillants", "Rosé", "Digestifs"
        ));
        categoryFilter.setValue("Toutes");

        // Filtre par niveau de stock
        stockStatusFilter.setItems(FXCollections.observableArrayList(
                "Tous", "En stock", "Stock faible", "Rupture"
        ));
        stockStatusFilter.setValue("Tous");

        // Réapplique les filtres à chaque changement
        categoryFilter.setOnAction(e -> applyFilters());
        stockStatusFilter.setOnAction(e -> applyFilters());
    }

    /**
     * Active le filtrage en temps réel sur le champ de recherche
     */
    private void setupSearchListener() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters();
        });
    }

    /**
     * Applique tous les filtres actifs sur la liste de produits
     */
    private void applyFilters() {
        filteredList.clear();

        String searchText = searchField.getText().toLowerCase();
        String category = categoryFilter.getValue();
        String status = stockStatusFilter.getValue();

        for (stock product : productList) {
            // Recherche dans le nom ou la catégorie
            boolean matchSearch = searchText.isEmpty() ||
                    product.getLabel().toLowerCase().contains(searchText) ||
                    product.getGenre().toLowerCase().contains(searchText);

            // Filtre catégorie
            boolean matchCategory = category.equals("Toutes") ||
                    product.getGenre().equals(category);

            // Filtre statut (≥10 = stock OK, 1-9 = faible, 0 = rupture)
            boolean matchStatus = status.equals("Tous") ||
                    (status.equals("En stock") && product.getAvailable_quantity() >= 10) ||
                    (status.equals("Stock faible") && product.getAvailable_quantity() > 0 && product.getAvailable_quantity() < 10) ||
                    (status.equals("Rupture") && product.getAvailable_quantity() == 0);

            // Ajoute si tous les critères sont remplis
            if (matchSearch && matchCategory && matchStatus) {
                filteredList.add(product);
            }
        }
    }

    /**
     * Remet tous les filtres à zéro
     */
    @FXML
    private void resetFilters() {
        searchField.clear();
        categoryFilter.setValue("Toutes");
        stockStatusFilter.setValue("Tous");
        applyFilters();
    }

    /**
     * Calcule et affiche le nombre total de bouteilles
     */
    private void totalStock(){
        int total = 0;
        for (stock product : productList) {
            total += product.getAvailable_quantity();
        }
        totalBottlesLabel.setText(total + " bouteilles en stock");
    }

    /**
     * Ouvre la fenêtre modale d'ajout de produit
     */
    @FXML
    private void showAddProductDialog() {
        try {
            // Chargement de la vue dialog
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/javafx/view/AddProductDialog.fxml"));
            Parent root = loader.load();

            AddProductDialogController dialogController = loader.getController();

            // Création de la fenêtre modale
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Ajouter un produit");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(tableStocks.getScene().getWindow());
            dialogStage.setScene(new Scene(root));
            dialogStage.setResizable(false);

            dialogStage.showAndWait();

            // Traitement si l'utilisateur a validé
            if (dialogController.isConfirmed()) {
                AddProductDialogController.ProductData data = dialogController.getProductData();

                // Création de l'objet stock
                stock newStock = new stock(
                        0, data.name, data.year, data.category, data.area,
                        data.quantity, data.price, data.supplierId, data.supplierName
                );

                // Envoi à l'API
                boolean success = APICall.addStock(newStock);

                if (success) {
                    // Rafraîchissement des données
                    refreshData();

                    // Confirmation
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Succès");
                    alert.setHeaderText(null);
                    alert.setContentText("Produit ajouté avec succès !");
                    alert.showAndWait();
                } else {
                    // Erreur
                    showError("Erreur lors de l'ajout du produit");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erreur : " + e.getMessage());
        }
    }
}
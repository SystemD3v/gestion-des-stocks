package com.example.javafx.Controller;

import com.example.javafx.model.Model;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Contrôleur de la fenêtre modale d'ajout de produit
 */
public class AddProductDialogController {

    // === Champs du formulaire ===
    @FXML private TextField nameField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private TextField yearField;
    @FXML private TextField quantityField;
    @FXML private TextField priceField;
    @FXML private TextField areaField;
    @FXML private ComboBox<String> supplierCombo;
    @FXML private Label errorLabel;

    // État de confirmation et données du produit
    @Getter
    private boolean confirmed = false;
    @Getter
    private ProductData productData;

    // Map pour associer nom du fournisseur à son ID
    private final Map<String, Integer> supplierMap = new HashMap<>();

    /**
     * Initialisation : charge les catégories et les fournisseurs
     */
    @FXML
    public void initialize() {
        // Configuration des catégories disponibles
        categoryCombo.setItems(FXCollections.observableArrayList(
                "Rouge", "Blanc", "Pétillants", "Rosé", "Digestifs"
        ));
        categoryCombo.setValue("Rouge");

        loadSuppliers();
    }

    /**
     * Charge la liste des fournisseurs depuis l'API
     */
    private void loadSuppliers() {
        try {
            List<Model.supplier> suppliers = APICall.retrieveSupplier("get_supplier");

            supplierMap.clear();

            // Création de la map nom -> ID
            for (Model.supplier supplier : suppliers) {
                supplierMap.put(supplier.supplier_name, supplier.id);
            }

            // Ajout des noms dans le ComboBox
            supplierCombo.setItems(FXCollections.observableArrayList(supplierMap.keySet()));

            // Sélection du premier fournisseur par défaut
            if (!supplierMap.isEmpty()) {
                supplierCombo.setValue(supplierMap.keySet().iterator().next());
            }

        } catch (Exception e) {
            errorLabel.setText("Erreur lors du chargement des fournisseurs: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Validation et création du produit lors du clic sur "Ajouter"
     */
    @FXML
    private void handleAdd() {
        errorLabel.setText("");

        // Validation du nom
        if (nameField.getText().trim().isEmpty()) {
            errorLabel.setText("Le nom du produit est requis");
            return;
        }

        // Validation du fournisseur
        if (supplierCombo.getValue() == null) {
            errorLabel.setText("Veuillez sélectionner un fournisseur");
            return;
        }

        try {
            // Parsing des valeurs numériques
            int year = Integer.parseInt(yearField.getText().trim());
            int quantity = Integer.parseInt(quantityField.getText().trim());
            double price = Double.parseDouble(priceField.getText().trim());

            // Validation de l'année
            if (year < 1900 || year > 2100) {
                errorLabel.setText("Année invalide");
                return;
            }

            // Validation de la quantité
            if (quantity < 0) {
                errorLabel.setText("La quantité ne peut pas être négative");
                return;
            }

            // Validation du prix
            if (price < 0) {
                errorLabel.setText("Le prix ne peut pas être négatif");
                return;
            }

            // Récupération de l'ID du fournisseur depuis la map
            String selectedSupplierName = supplierCombo.getValue();
            int supplierId = supplierMap.get(selectedSupplierName);

            // Création de l'objet de données
            String supplierName = "";
            productData = new ProductData(
                    nameField.getText().trim(),
                    categoryCombo.getValue(),
                    year,
                    quantity,
                    price,
                    areaField.getText().trim(),
                    supplierId,
                    supplierName
            );

            confirmed = true;
            closeDialog();

        } catch (NumberFormatException e) {
            errorLabel.setText("Veuillez entrer des valeurs numériques valides");
        }
    }

    /**
     * Annule l'ajout et ferme la fenêtre
     */
    @FXML
    private void handleCancel() {
        confirmed = false;
        closeDialog();
    }

    /**
     * Ferme la fenêtre modale
     */
    private void closeDialog() {
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    /**
     * Classe de données pour transférer les informations du produit
     */
    public static class ProductData {
        public final String name;
        public final String category;
        public final int year;
        public final int quantity;
        public final double price;
        public final String area;
        public final int supplierId;
        public final String supplierName;

        public ProductData(String name, String category, int year, int quantity,
                           double price, String area, int supplierId, String supplierName) {
            this.name = name;
            this.category = category;
            this.year = year;
            this.quantity = quantity;
            this.price = price;
            this.area = area;
            this.supplierId = supplierId;
            this.supplierName = supplierName;
        }
    }
}
package com.example.javafx.Controller;

import com.example.javafx.model.Model.Instance;
import com.example.javafx.model.Model.user;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.event.ActionEvent;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

public class ClientsController {
    @FXML
    private TableView<user> tableClients;
    @FXML
    private TableColumn<Instance, Integer> clientidColumn;
    @FXML
    private TableColumn<Instance, String> firstnameColumn;
    @FXML
    private TableColumn<Instance, String> lastnameColumn;

    // Detail panel components
    @FXML
    private VBox userDetailsPanel;
    @FXML
    private Label detailUid;
    @FXML
    private Label detailFirstname;
    @FXML
    private Label detailLastname;
    @FXML
    private Label detailEmail;
    @FXML
    private Label detailPhone;
    @FXML
    private Label detailAddress;
    @FXML
    private Label detailRole;
    @FXML
    private Label detailBottles;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() throws Exception {
        clientidColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstnameColumn.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        lastnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastname"));

        List<user> instancesFromApi = APICall.retrieveUser("get_users");

        System.out.println(instancesFromApi);

        ObservableList<user> data = FXCollections.observableArrayList(instancesFromApi);

        tableClients.setItems(data);

        // Add selection listener to the table
        tableClients.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                displayUserDetails(newSelection);
            }
        });
    }

    /**
     * Displays the selected user's details in the right panel
     */
    private void displayUserDetails(user client) {
        if (client != null) {
            userDetailsPanel.setVisible(true);
            detailUid.setText(String.valueOf(client.getId()));
            detailFirstname.setText(client.getFirstname() != null ? client.getFirstname() : "N/A");
            detailLastname.setText(client.getLastname() != null ? client.getLastname() : "N/A");
            detailEmail.setText(client.getEmail() != null ? client.getEmail() : "N/A");
            detailPhone.setText(client.getPhone_number() != null ? client.getPhone_number() : "N/A");
            detailAddress.setText(client.getAddress() != null ? client.getAddress() : "N/A");
            detailRole.setText(client.getRole() != null ? client.getRole() : "N/A");
            detailBottles.setText(String.valueOf(client.getTotal_bottles_bought() != null ? client.getTotal_bottles_bought() : 0));
        }
    }

    /**
     * Handles the create user button action
     */
    @FXML
    private void handleCreateUser(ActionEvent event) {
        // Create dialog
        Dialog<user> dialog = new Dialog<>();
        dialog.setTitle("Créer un nouveau client");
        dialog.setHeaderText("Saisir les informations du nouveau client");

        ButtonType createButtonType = new ButtonType("Créer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtonType, ButtonType.CANCEL);

        // Create form fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstnameField = new TextField();
        firstnameField.setPromptText("Prénom");
        TextField lastnameField = new TextField();
        lastnameField.setPromptText("Nom");
        TextField emailField = new TextField();
        emailField.setPromptText("email@example.com");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Mot de passe");
        TextField addressField = new TextField();
        addressField.setPromptText("Adresse complète");
        TextField phoneField = new TextField();
        phoneField.setPromptText("Numéro de téléphone");
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("client", "admin", "employee");
        roleComboBox.setValue("client");
        TextField bottlesField = new TextField("0");
        bottlesField.setPromptText("Nombre de bouteilles");

        grid.add(new Label("Prénom:*"), 0, 0);
        grid.add(firstnameField, 1, 0);
        grid.add(new Label("Nom:*"), 0, 1);
        grid.add(lastnameField, 1, 1);
        grid.add(new Label("Email:*"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Mot de passe:*"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("Téléphone:"), 0, 4);
        grid.add(phoneField, 1, 4);
        grid.add(new Label("Adresse:"), 0, 5);
        grid.add(addressField, 1, 5);
        grid.add(new Label("Rôle:"), 0, 6);
        grid.add(roleComboBox, 1, 6);
        grid.add(new Label("Bouteilles:"), 0, 7);
        grid.add(bottlesField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Enable/disable create button depending on whether required fields are filled
        javafx.scene.Node createButton = dialog.getDialogPane().lookupButton(createButtonType);
        createButton.setDisable(true);

        // Validation listener
        Runnable validateFields = () -> {
            createButton.setDisable(
                    firstnameField.getText().trim().isEmpty() ||
                            lastnameField.getText().trim().isEmpty() ||
                            emailField.getText().trim().isEmpty() ||
                            passwordField.getText().trim().isEmpty()
            );
        };

        firstnameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields.run());
        lastnameField.textProperty().addListener((observable, oldValue, newValue) -> validateFields.run());
        emailField.textProperty().addListener((observable, oldValue, newValue) -> validateFields.run());
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> validateFields.run());

        // Convert result when create button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                try {
                    return new user(
                            null, // ID will be generated by backend
                            lastnameField.getText().trim(),
                            firstnameField.getText().trim(),
                            Integer.parseInt(bottlesField.getText().trim()),
                            emailField.getText().trim(),
                            passwordField.getText().trim(),
                            roleComboBox.getValue(),
                            addressField.getText().trim(),
                            phoneField.getText().trim()
                    );
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Le nombre de bouteilles doit être un nombre valide.");
                    return null;
                }
            }
            return null;
        });

        Optional<user> result = dialog.showAndWait();

        result.ifPresent(newUser -> {
            if (newUser != null) {
                try {
                    // Create user via API
                    createUser(newUser);

                    // Refresh the table
                    refreshTable();

                    showAlert(Alert.AlertType.INFORMATION, "Succès",
                            "Le client a été créé avec succès.");

                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Erreur lors de la création du client: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Handles the edit profile button action
     */
    @FXML
    private void handleEditProfile(ActionEvent event) {
        user selectedUser = tableClients.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun client sélectionné",
                    "Veuillez sélectionner un client à modifier.");
            return;
        }

        // Create edit dialog
        Dialog<user> dialog = new Dialog<>();
        dialog.setTitle("Modifier le profil");
        dialog.setHeaderText("Modifier les informations du client");

        ButtonType saveButtonType = new ButtonType("Enregistrer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

        // Create form fields with current values
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField firstnameField = new TextField(selectedUser.getFirstname());
        TextField lastnameField = new TextField(selectedUser.getLastname());
        TextField emailField = new TextField(selectedUser.getEmail());
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Laisser vide pour ne pas changer");
        TextField phoneField = new TextField(selectedUser.getPhone_number());
        TextField addressField = new TextField(selectedUser.getAddress());
        ComboBox<String> roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("client", "admin", "employee");
        roleComboBox.setValue(selectedUser.getRole());
        TextField bottlesField = new TextField(String.valueOf(selectedUser.getTotal_bottles_bought()));

        grid.add(new Label("Prénom:"), 0, 0);
        grid.add(firstnameField, 1, 0);
        grid.add(new Label("Nom:"), 0, 1);
        grid.add(lastnameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Mot de passe:"), 0, 3);
        grid.add(passwordField, 1, 3);
        grid.add(new Label("Téléphone:"), 0, 4);
        grid.add(phoneField, 1, 4);
        grid.add(new Label("Adresse:"), 0, 5);
        grid.add(addressField, 1, 5);
        grid.add(new Label("Rôle:"), 0, 6);
        grid.add(roleComboBox, 1, 6);
        grid.add(new Label("Bouteilles:"), 0, 7);
        grid.add(bottlesField, 1, 7);

        dialog.getDialogPane().setContent(grid);

        // Convert result when save button is clicked
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                try {
                    String password = passwordField.getText().trim().isEmpty() ?
                            selectedUser.getPassword() : passwordField.getText().trim();

                    return new user(
                            selectedUser.getId(),
                            lastnameField.getText().trim(),
                            firstnameField.getText().trim(),
                            Integer.parseInt(bottlesField.getText().trim()),
                            emailField.getText().trim(),
                            password,
                            roleComboBox.getValue(),
                            addressField.getText().trim(),
                            phoneField.getText().trim()
                    );
                } catch (NumberFormatException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Le nombre de bouteilles doit être un nombre valide.");
                    return null;
                }
            }
            return null;
        });

        Optional<user> result = dialog.showAndWait();

        result.ifPresent(updatedUser -> {
            if (updatedUser != null) {
                try {
                    // Update each field via API (with URL encoding for special characters)
                    updateUserField(updatedUser.getId(), "firstname", updatedUser.getFirstname().replace("+", "%20"));
                    updateUserField(updatedUser.getId(), "lastname", updatedUser.getLastname().replace("+", "%20"));
                    updateUserField(updatedUser.getId(), "email", updatedUser.getEmail().replace("+", "%20"));
                    updateUserField(updatedUser.getId(), "phone_number", updatedUser.getPhone_number().replace("+", "%20"));
                    updateUserField(updatedUser.getId(), "address", updatedUser.getAddress().replace("+", "%20"));
                    updateUserField(updatedUser.getId(), "role", updatedUser.getRole().replace("+", "%20"));
                    updateUserField(updatedUser.getId(), "total_bottles_bought", String.valueOf(updatedUser.getTotal_bottles_bought()).replace("+", "%20"));

                    // Only update password if it was changed
                    if (!passwordField.getText().trim().isEmpty()) {
                        updateUserField(updatedUser.getId(), "password", updatedUser.getPassword());
                    }

                    // Refresh the table
                    refreshTable();

                    // Fetch the updated user and display details
                    user refreshedUser = getUserById(updatedUser.getId());
                    if (refreshedUser != null) {
                        displayUserDetails(refreshedUser);
                    }

                    showAlert(Alert.AlertType.INFORMATION, "Succès",
                            "Le profil a été modifié avec succès.");

                } catch (Exception e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur",
                            "Erreur lors de la modification du profil: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Handles the delete profile button action
     */
    @FXML
    private void handleDeleteProfile(ActionEvent event) {
        user selectedUser = tableClients.getSelectionModel().getSelectedItem();

        if (selectedUser == null) {
            showAlert(Alert.AlertType.WARNING, "Aucun client sélectionné",
                    "Veuillez sélectionner un client à supprimer.");
            return;
        }

        // Show confirmation dialog
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirmation de suppression");
        confirmDialog.setHeaderText("Supprimer le client " + selectedUser.getFirstname() + " " + selectedUser.getLastname() + "?");
        confirmDialog.setContentText("Cette action est irréversible. Êtes-vous sûr de vouloir continuer?");

        Optional<ButtonType> result = confirmDialog.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            try {
                // Delete user via API
                deleteUser(selectedUser.getId());

                // Remove from table
                tableClients.getItems().remove(selectedUser);

                // Hide details panel
                userDetailsPanel.setVisible(false);

                showAlert(Alert.AlertType.INFORMATION, "Succès",
                        "Le profil a été supprimé avec succès.");

            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Erreur",
                        "Erreur lors de la suppression du profil: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets a user by ID from the API
     */
    private user getUserById(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/get_user_by_id/" + id))
                .GET()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            Gson gson = new Gson();
            // The API returns a list, so we parse it and get the first element
            String responseBody = response.body();
            if (!responseBody.startsWith("[")) {
                responseBody = "[" + responseBody + "]";
            }
            List<user> users = gson.fromJson(responseBody, new com.google.gson.reflect.TypeToken<List<user>>(){}.getType());
            return users.isEmpty() ? null : users.get(0);
        }
        return null;
    }

    /**
     * Creates a new user via API
     */
    private void createUser(user newUser) throws Exception {
        Gson gson = new Gson();
        String jsonBody = gson.toJson(newUser);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/create_user"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 201 && response.statusCode() != 200) {
            throw new Exception("Erreur HTTP: " + response.statusCode());
        }
    }

    /**
     * Updates a single field of a user via API with proper URL encoding
     */
    private void updateUserField(Integer id, String field, String value) throws Exception {
        // URL encode the value to handle spaces and special characters
        String encodedValue = URLEncoder.encode(value, StandardCharsets.UTF_8);
        String apiUrl = "updateUser/" + id + "/" + field + "/" + encodedValue;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/" + apiUrl))
                .GET()
                .build();

        HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
    }

    /**
     * Deletes a user via API
     */
    private void deleteUser(Integer id) throws Exception {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v1/delete_users/" + id))
                .DELETE()
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Erreur lors de la suppression: " + response.statusCode());
        }
    }

    /**
     * Refreshes the table data from the API
     */
    private void refreshTable() throws Exception {
        List<user> instancesFromApi = APICall.retrieveUser("get_users");
        ObservableList<user> data = FXCollections.observableArrayList(instancesFromApi);
        tableClients.setItems(data);

        // Reselect the updated user if possible
        user selectedUser = tableClients.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            for (user u : data) {
                if (u.getId().equals(selectedUser.getId())) {
                    tableClients.getSelectionModel().select(u);
                    break;
                }
            }
        }
    }

    /**
     * Shows an alert dialog
     */
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
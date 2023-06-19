package com.example.prueba100;

import DAO.AdminDAO;
import DAO.ClientDAO;
import connections.ConnectionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.controlsfx.control.Notifications;
import util.Method;

import java.io.IOException;

public class LoginController {

    @FXML
    private Pane shopView;

    @FXML
    private Button btn_login;

    @FXML
    private Label labelUser;

    @FXML
    private PasswordField txt_password;

    @FXML
    private TextField txt_username;

    @FXML
    private ComboBox<String> type_up;

    @FXML
    private void initialize() {
        // Initialize the type_up combo box with options
        ObservableList<String> typeOptions = FXCollections.observableArrayList("admin", "client");
        type_up.setItems(typeOptions);
        type_up.getSelectionModel().selectFirst();
    }

    @FXML
    private void btnHomeValidate(ActionEvent event) throws IOException {
        // Retrieve the entered username, password, and selected type
        String username = txt_username.getText();
        String password = txt_password.getText();
        String selectedType = type_up.getValue();

        // Establish a connection to the MySQL database
        ConnectionMySQL conexionBD = new ConnectionMySQL();

        try {
            if (selectedType.equalsIgnoreCase("admin")) {
                // Validate credentials in the "admin" table
                AdminDAO adminDAO = new AdminDAO(conexionBD);
                boolean credencialesValidas = adminDAO.validarCredenciales(username, password);
                if (credencialesValidas) {
                    // Username and password are correct for the admin
                    Notifications.create().title(Method.Constants.CORRECT).text(Method.Constants.CORRECT).showWarning();
                    App.setRoot("Inicio");
                } else {
                    // Incorrect username or password for the admin
                    Notifications.create().title(Method.Constants.INCORRECT).text(Method.Constants.INCORRECT).showWarning();
                }
            } else if (selectedType.equalsIgnoreCase("client")) {
                // Validate credentials in the "client" table
                ClientDAO clientDAO = new ClientDAO(conexionBD);
                boolean credencialesValidas = clientDAO.validarCredenciales(username, password);
                if (credencialesValidas) {
                    // Username and password are correct for the client
                    Notifications.create().title(Method.Constants.CORRECT).text(Method.Constants.CORRECT).showWarning();

                    // Load the FXML file of the ShopController
                    FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("Shop.fxml"));
                    Parent shopRoot = shopLoader.load();
                    ShopController shopController = shopLoader.getController();
                    shopController.setUsername(username);

                    // Get the current scene and set the root as the ShopController
                    Scene scene = btn_login.getScene();
                    scene.setRoot(shopRoot);
                } else {
                    // Incorrect username or password for the client
                    Notifications.create().title(Method.Constants.INCORRECT).text(Method.Constants.INCORRECT).showWarning();
                }
            } else {
                // Invalid selected type
                Notifications.create().title(Method.Constants.INCORRECT).text("Tipo seleccionado no válido").showWarning();
            }
        } catch (Exception e) {
            // Exception handling
            e.printStackTrace(); // Optional: Print information about the exception
        } finally {
            // Close the connection
            if (conexionBD != null) {
                conexionBD.close();
            }
        }
    }

    @FXML
    void btnSignUp(ActionEvent event) throws IOException {
        App.setRoot("Orders");
    }
}


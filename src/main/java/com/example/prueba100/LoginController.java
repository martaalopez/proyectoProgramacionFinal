package com.example.prueba100;

import DAO.AdminDAO;
import DAO.ClientDAO;
import connections.ConnectionMySQL;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.controlsfx.control.Notifications;
import util.Method;

import java.io.IOException;

public class LoginController {

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
        ObservableList<String> typeOptions = FXCollections.observableArrayList("admin", "client");
        type_up.setItems(typeOptions);
        type_up.getSelectionModel().selectFirst();
    }

    @FXML
    private void btnHomeValidate(ActionEvent event) throws IOException {
        String username = txt_username.getText();
        String password = txt_password.getText();
        String selectedType = type_up.getValue();

        ConnectionMySQL conexionBD = new ConnectionMySQL();

        try {
            if (selectedType.equalsIgnoreCase("admin")) {
                // Validate credentials in the "admin" table
                AdminDAO adminDAO = new AdminDAO(conexionBD);
                boolean credencialesValidas = adminDAO.validarCredenciales(username, password);
                if (credencialesValidas) {
                    // Username and password are correct for the administrator
                    Notifications.create().title(Method.Constants.CORRECT).text(Method.Constants.CORRECT).showWarning();
                    App.setRoot("Inicio");
                } else {
                    // Incorrect username or password for the administrator
                    Notifications.create().title(Method.Constants.INCORRECT).text(Method.Constants.INCORRECT).showWarning();
                }
            } else if (selectedType.equalsIgnoreCase("client")) {
                // Validate credentials in the "client" table
                ClientDAO clientDAO = new ClientDAO(conexionBD);
                boolean credencialesValidas = clientDAO.validarCredenciales(username, password);
                if (credencialesValidas) {
                    // Username and password are correct for the client
                    Notifications.create().title(Method.Constants.CORRECT).text(Method.Constants.CORRECT).showWarning();
                    App.setRoot("Shop");
                } else {
                    // Incorrect username or password for the client
                    Notifications.create().title(Method.Constants.INCORRECT).text(Method.Constants.INCORRECT).showWarning();
                }
            } else {
                // Invalid selected type
                Notifications.create().title(Method.Constants.INCORRECT).text("Invalid selected type").showWarning();
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

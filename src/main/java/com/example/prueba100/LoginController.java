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
                // Validar credenciales en la tabla "admin"
                AdminDAO adminDAO = new AdminDAO(conexionBD);
                boolean credencialesValidas = adminDAO.validarCredenciales(username, password);
                if (credencialesValidas) {
                    // Usuario y contraseña son correctos para el administrador
                    Notifications.create().title(Method.Constants.CORRECT).text(Method.Constants.CORRECT).showWarning();
                    App.setRoot("Inicio");
                } else {
                    // Usuario o contraseña incorrectos para el administrador
                    Notifications.create().title(Method.Constants.INCORRECT).text(Method.Constants.INCORRECT).showWarning();
                }
            } else if (selectedType.equalsIgnoreCase("client")) {
                // Validar credenciales en la tabla "client"
                ClientDAO clientDAO = new ClientDAO(conexionBD);
                boolean credencialesValidas = clientDAO.validarCredenciales(username, password);
                if (credencialesValidas) {
                    // Usuario y contraseña son correctos para el cliente
                    Notifications.create().title(Method.Constants.CORRECT).text(Method.Constants.CORRECT).showWarning();

                    // Cargar el archivo FXML del ShopController
                    FXMLLoader shopLoader = new FXMLLoader(getClass().getResource("Shop.fxml"));
                    Parent shopRoot = shopLoader.load();
                    ShopController shopController = shopLoader.getController();
                    shopController.setUsername(username);

                    // Obtener la escena actual y establecer la raíz como el ShopController
                    Scene scene = btn_login.getScene();
                    scene.setRoot(shopRoot);
                } else {
                    // Usuario o contraseña incorrectos para el cliente
                    Notifications.create().title(Method.Constants.INCORRECT).text(Method.Constants.INCORRECT).showWarning();
                }
            } else {
                // Tipo seleccionado no válido
                Notifications.create().title(Method.Constants.INCORRECT).text("Tipo seleccionado no válido").showWarning();
            }
        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace(); // Opcional: Imprimir información sobre la excepción
        } finally {
            // Cerrar la conexión
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
